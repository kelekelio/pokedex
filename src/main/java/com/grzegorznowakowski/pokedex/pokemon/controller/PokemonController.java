package com.grzegorznowakowski.pokedex.pokemon.controller;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemon.model.PokemonModelAssembler;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonPagesRepository;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonRepository;
import com.grzegorznowakowski.pokedex.type.entity.Type;
import com.grzegorznowakowski.pokedex.type.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final TypeRepository typeRepository;

    private final PokemonPagesRepository pokemonPagesRepository;

    private final PagedResourcesAssembler<PokemonEntity> pagedResourcesAssembler;


    PokemonController(PokemonRepository repository,
                      PokemonPagesRepository pokemonPagesRepository,
                      PokemonModelAssembler assembler,
                      TypeRepository typeRepository,
                      PagedResourcesAssembler<PokemonEntity> pagedResourcesAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.typeRepository = typeRepository;
        this.pokemonPagesRepository = pokemonPagesRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    /*
    @GetMapping("/pokemon")
    public CollectionModel<EntityModel<PokemonEntity>> all() {

        List<EntityModel<PokemonEntity>> pokemons = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pokemons, linkTo(methodOn(PokemonController.class).all()).withSelfRel());
    }

     */

    @GetMapping("/pokemon")
    public ResponseEntity<PagedModel<EntityModel<PokemonEntity>>> getAllPokemon(Pageable pageable) {
        Page<PokemonEntity> poemons = pokemonPagesRepository.findAll(pageable);

        PagedModel<EntityModel<PokemonEntity>> model = pagedResourcesAssembler
                .toModel(poemons, assembler);

        return new ResponseEntity<>(model, HttpStatus.OK);


    }




    @GetMapping("/pokemon/{id}")
    public EntityModel<PokemonEntity> one(@PathVariable Integer id) {
        PokemonEntity pokemon = repository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));

        return assembler.toModel(pokemon);
    }


    @PostMapping("/pokemon")
    ResponseEntity<?> newPokemon(@RequestBody PokemonEntity newPokemon) {

        List<Type> allTypes = typeRepository.findAll();
        for (Type pokeType: newPokemon.getTypes()) {
            if (allTypes.stream().noneMatch(o -> o.getId().equals(pokeType.getId()))) {
                typeRepository.save(pokeType);
            }
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

}
