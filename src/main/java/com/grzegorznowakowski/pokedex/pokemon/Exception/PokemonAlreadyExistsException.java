package com.grzegorznowakowski.pokedex.pokemon.Exception;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonAlreadyExistsException extends RuntimeException {
    public PokemonAlreadyExistsException (String name) {
        super("A Pokemon with a name \"" + name + "\" already exists!");
    }
}
