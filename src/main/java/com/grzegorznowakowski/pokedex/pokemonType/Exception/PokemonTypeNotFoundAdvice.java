package com.grzegorznowakowski.pokedex.pokemonType.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Grzegorz Nowakowski
 */
@ControllerAdvice
public class PokemonTypeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PokemonTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pokemonTypeNotFound(PokemonTypeNotFoundException e) {
        return e.getMessage();
    }
}