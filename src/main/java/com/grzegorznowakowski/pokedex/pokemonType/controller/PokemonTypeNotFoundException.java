package com.grzegorznowakowski.pokedex.pokemonType.controller;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonTypeNotFoundException extends RuntimeException {
    PokemonTypeNotFoundException (Long id) {
        super("Type with " + id +  " ID could not be found.");
    }
    PokemonTypeNotFoundException (String name) {
        super(name + " type could not be found.");
    }
}