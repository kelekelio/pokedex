package com.grzegorznowakowski.pokedex.pokemon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Grzegorz Nowakowski
 */
@ControllerAdvice
public class PokemonAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(PokemonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String pokemonAlreadyExists(PokemonAlreadyExistsException e) {
        return e.getMessage();
    }
}
