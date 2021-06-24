package com.grzegorznowakowski.pokedex.pokemon.Exception;

/**
 * @author Grzegorz Nowakowski
 */
public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException (Integer id) {
        super("Pokemon with " + id +  " ID could not be found.");
    }
    public PokemonNotFoundException (String name) {
        super(name + " Pokemon could not be found.");
    }
    public PokemonNotFoundException() {
        super("No Pokemon matching specified criteria could be found.");
    }
}
