package com.grzegorznowakowski.pokedex.pokemonType.model;

import com.grzegorznowakowski.pokedex.pokemon.controller.PokemonController;
import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemonType.controller.PokemonTypeController;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Grzegorz Nowakowski
 */
@Component
public class PokemonTypeModelAssembler implements RepresentationModelAssembler<PokemonType, EntityModel<PokemonType>> {

    //TODO instead of all(), use alltypes()
    @Override
    public EntityModel<PokemonType> toModel(PokemonType pokemonType) {

        return EntityModel.of(pokemonType, //
                linkTo(methodOn(PokemonTypeController.class).one(pokemonType.getId())).withSelfRel(),
                linkTo(methodOn(PokemonTypeController.class).all()).withRel("types"));
    }
}
