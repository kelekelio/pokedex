package com.grzegorznowakowski.pokedex.pokemon.controller;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemon.model.PokemonModelAssembler;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonPagesRepository;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonRepository;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import com.grzegorznowakowski.pokedex.pokemonType.repository.PokemonTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
@RequestMapping("/api")
public class PokemonController {

    private final PokemonRepository repository;

    private final PokemonModelAssembler assembler;

    private final PokemonTypeRepository typeRepository;

    private final PokemonPagesRepository pokemonPagesRepository;

    private final PagedResourcesAssembler<PokemonEntity> pagedResourcesAssembler;


    PokemonController(PokemonRepository repository,
                      PokemonPagesRepository pokemonPagesRepository,
                      PokemonModelAssembler assembler,
                      PokemonTypeRepository typeRepository,
                      PagedResourcesAssembler<PokemonEntity> pagedResourcesAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.typeRepository = typeRepository;
        this.pokemonPagesRepository = pokemonPagesRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }



    @GetMapping("/allpokemon")
    public CollectionModel<EntityModel<PokemonEntity>> all() {

        List<EntityModel<PokemonEntity>> pokemons = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pokemons, linkTo(methodOn(PokemonController.class).all()).withSelfRel());
    }



    @GetMapping("/pokemon")
    public ResponseEntity<PagedModel<EntityModel<PokemonEntity>>> getAllPokemon(Pageable pageable) {
        Page<PokemonEntity> pokemons = pokemonPagesRepository.findAll(pageable);

        PagedModel<EntityModel<PokemonEntity>> model = pagedResourcesAssembler
                .toModel(pokemons, assembler);

        return new ResponseEntity<>(model, HttpStatus.OK);


    }

    @GetMapping("/pokemon/type/{type}")
    public ResponseEntity<PagedModel<EntityModel<PokemonEntity>>> getAllPokemonByType(@PathVariable String type, Pageable pageable) {
        Page<PokemonEntity> pokemons = pokemonPagesRepository.findByType(type, pageable);

        PagedModel<EntityModel<PokemonEntity>> model = pagedResourcesAssembler
                .toModel(pokemons, assembler);

        return new ResponseEntity<>(model, HttpStatus.OK);


    }





    /*
    @GetMapping("/pokemon/{id}")
    public EntityModel<PokemonEntity> one(@PathVariable Integer id) {
        PokemonEntity pokemon = repository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));
        return assembler.toModel(pokemon);
    }

    @GetMapping("/pokemon/name/{name}")
    public EntityModel<PokemonEntity> oneByName(@PathVariable String name) {
        PokemonEntity pokemon = repository.findByName(name).orElseThrow(() -> new PokemonNotFoundException(name));
        return assembler.toModel(pokemon);
    }

     */

    @GetMapping("/pokemon/{value}")
    public EntityModel<PokemonEntity> one(@PathVariable String value) {
        PokemonEntity pokemon;

        if(value.matches("\\d*")){
            pokemon = repository.findById(Integer.valueOf(value)).orElseThrow(() -> new PokemonNotFoundException(Integer.valueOf(value)));
        } else {
            pokemon = repository.findByName(value).orElseThrow(() -> new PokemonNotFoundException(value));
        }

        return assembler.toModel(pokemon);
    }


    @PostMapping("/pokemon")
    ResponseEntity<?> newPokemon(@RequestBody PokemonEntity newPokemon) {

        List<PokemonType> allTypes = typeRepository.findAll();
        for (PokemonType pokeType: newPokemon.getTypes()) {
            if (allTypes.stream().noneMatch(o -> o.getId().equals(pokeType.getId()))) {
                typeRepository.save(pokeType);
            }
        }

        if (repository.findByName(newPokemon.getName()).isPresent()) {
            throw new PokemonAlreadyExistsException(newPokemon.getName());
        }

        EntityModel<PokemonEntity> entityModel = assembler.toModel(repository.save(newPokemon));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @PutMapping("/pokemon/{id}")
    ResponseEntity<?> replacePokemon(@RequestBody PokemonEntity newPokemon, @PathVariable Integer id) {

        PokemonEntity updatedPokemon = repository.findById(id)
                .map(pokemon -> {
                    pokemon.setName(newPokemon.getName());
                    return repository.save(pokemon);
                })
                .orElseGet(() -> {
                    newPokemon.setId(id);
                    return repository.save(newPokemon);
                });

        EntityModel<PokemonEntity> entityModel = assembler.toModel(updatedPokemon);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/pokemon/{id}")
    ResponseEntity<?> deletePokemon(@PathVariable Integer id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/pokemon/test")
    ResponseEntity<?> newtestPokemon(@RequestBody PokemonEntity newPokemon) {

        PokemonEntity pokemon1 = new PokemonEntity(100, "test", newPokemon.getTypes());

        EntityModel<PokemonEntity> entityModel = assembler.toModel(repository.save(pokemon1));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }








}
