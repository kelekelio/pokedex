package com.grzegorznowakowski.pokedex.pokemonType.Exception;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonTypeNotFoundException extends RuntimeException {
    public PokemonTypeNotFoundException(Long id) {
        super("Type with " + id +  " ID could not be found.");
    }
    public PokemonTypeNotFoundException (String name) {
        super(name + " type could not be found.");
    }
}