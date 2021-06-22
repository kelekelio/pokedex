package com.grzegorznowakowski.pokedex.type.controller;

/**
 * @author Grzegorz Nowakowski
 */
public class TypeNotFoundException extends RuntimeException {
    TypeNotFoundException (Long id) {
        super("Type with " + id +  " ID could not be found.");
    }
    TypeNotFoundException (String name) {
        super(name + " type could not be found.");
    }
}