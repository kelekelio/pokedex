package com.grzegorznowakowski.pokedex.pokemon.controller;

public class PokemonNotFoundException extends RuntimeException {

    PokemonNotFoundException (Integer id) {
        super("Pokemon with " + id +  " ID could not be found.");
    }
}
