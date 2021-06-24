package com.grzegorznowakowski.pokedex.pokemonType.controller;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemonType.Exception.PokemonTypeNotFoundException;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import com.grzegorznowakowski.pokedex.pokemonType.model.PokemonTypeModelAssembler;
import com.grzegorznowakowski.pokedex.pokemonType.repository.PokemonTypePagesRepository;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
@RequestMapping("/api")
public class PokemonTypeController {

    private final PokemonTypeRepository pokemonTypeRepository;

    private final PokemonTypeModelAssembler pokemonTypeModelAssembler;

    private final PagedResourcesAssembler<PokemonType> pagedResourcesAssembler;

    private final PokemonTypePagesRepository pokemonTypePagesRepository;

    public PokemonTypeController(PokemonTypeRepository pokemonTypeRepository,
                                 PokemonTypeModelAssembler pokemonTypeModelAssembler,
                                 PagedResourcesAssembler<PokemonType> pagedResourcesAssembler,
                                 PokemonTypePagesRepository pokemonTypePagesRepository) {
        this.pokemonTypeRepository = pokemonTypeRepository;
        this.pokemonTypeModelAssembler = pokemonTypeModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.pokemonTypePagesRepository = pokemonTypePagesRepository;
    }

    //TODO remove alltypes if possible
    @GetMapping("/type")
    public ResponseEntity<PagedModel<EntityModel<PokemonType>>> getAllPokemonTypes(Pageable pageable) {
        Page<PokemonType> types = pokemonTypePagesRepository.findAll(pageable);

        PagedModel<EntityModel<PokemonType>> model = pagedResourcesAssembler
                .toModel(types, pokemonTypeModelAssembler);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/alltypes")
    public CollectionModel<EntityModel<PokemonType>> all() {

        List<EntityModel<PokemonType>> pokemonTypes = pokemonTypeRepository.findAll().stream()
                .map(pokemonTypeModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pokemonTypes, linkTo(methodOn(PokemonTypeController.class).all()).withSelfRel());
    }

    @GetMapping("/type/{id}")
    public EntityModel<PokemonType> one(@PathVariable Long id) {
        PokemonType type = pokemonTypeRepository.findById(id).orElseThrow(() -> new PokemonTypeNotFoundException(id));

        return pokemonTypeModelAssembler.toModel(type);
    }

    @PostMapping("/type")
    ResponseEntity<?> newPokemonType(@RequestBody PokemonType newPokemonType) {

        EntityModel<PokemonType> entityModel = pokemonTypeModelAssembler.toModel(pokemonTypeRepository.save(newPokemonType));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/type/{id}")
    ResponseEntity<?> replacePokemonType(@RequestBody PokemonType newPokemonType, @PathVariable Long id) {

        PokemonType updatedPokemonType = pokemonTypeRepository.findById(id)
                .map(type -> {
                    type.setName(newPokemonType.getName());
                    return pokemonTypeRepository.save(type);
                })
                .orElseGet(() -> {
                    newPokemonType.setId(id);
                    return pokemonTypeRepository.save(newPokemonType);
                });

        EntityModel<PokemonType> entityModel = pokemonTypeModelAssembler.toModel(updatedPokemonType);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/type/{id}")
    ResponseEntity<?> deletePokemonType(@PathVariable Long id) {

        pokemonTypeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
