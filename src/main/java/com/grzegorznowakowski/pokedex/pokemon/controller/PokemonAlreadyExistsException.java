package com.grzegorznowakowski.pokedex.pokemon.controller;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonAlreadyExistsException extends RuntimeException {
    PokemonAlreadyExistsException (String name) {
        super("A Pokemon with a name \"" + name + "\" already exists!");
    }
}
