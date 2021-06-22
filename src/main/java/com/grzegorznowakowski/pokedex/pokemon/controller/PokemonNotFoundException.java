package com.grzegorznowakowski.pokedex.pokemon.controller;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonNotFoundException extends RuntimeException {
    PokemonNotFoundException (Integer id) {
        super("Pokemon with " + id +  " ID could not be found.");
    }
    PokemonNotFoundException (String name) {
        super(name + " Pokemon could not be found.");
    }
    PokemonNotFoundException() {
        super("No Pokemon matching specified criteria could be found.");
    }
}
