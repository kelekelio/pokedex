package com.grzegorznowakowski.pokedex.pokemon.model;

import com.grzegorznowakowski.pokedex.pokemon.controller.PokemonController;
import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * @author Grzegorz Nowakowski
 */
@Component
public class PokemonModelAssembler implements RepresentationModelAssembler<PokemonEntity, EntityModel<PokemonEntity>> {

    //TODO instead of all(), use getAllPokemon()
    @Override
    public EntityModel<PokemonEntity> toModel(PokemonEntity pokemon) {

        return EntityModel.of(pokemon, //
                linkTo(methodOn(PokemonController.class).one(pokemon.getId().toString())).withSelfRel(),
                linkTo(methodOn(PokemonController.class).all()).withRel("pokemon"));
    }
}
