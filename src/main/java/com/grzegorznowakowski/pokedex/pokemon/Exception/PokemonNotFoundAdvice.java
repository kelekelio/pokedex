package com.grzegorznowakowski.pokedex.pokemon.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Grzegorz Nowakowski
 */
@ControllerAdvice
public class PokemonNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PokemonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pokemonNotFound(PokemonNotFoundException e) {
        return e.getMessage();
    }
}
