package com.grzegorznowakowski.pokedex.pokemon.controller;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
public class PokemonController {

    private final PokemonRepository repository;

    PokemonController(PokemonRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/api/pokemons")
    List<PokemonEntity> all() {
        return repository.findAll();
    }

    @PostMapping("/api/pokemons")
    PokemonEntity newPokemon(@RequestBody PokemonEntity newPokemon) {
        return repository.save(newPokemon);
    }

    @GetMapping("/api/pokemons/{id}")
    PokemonEntity findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));
    }

    @PutMapping("/api/pokemons/{id}")
    PokemonEntity replacePokemon(@RequestBody PokemonEntity newPokemon, @PathVariable Integer id) {
        return repository.findById(id)
                .map(pokemon -> {
                    pokemon.setName(newPokemon.getName());
                    return repository.save(pokemon);
                })
                .orElseGet(() -> {
                    newPokemon.setId(id);
                    return repository.save(newPokemon);
                        });
    }

    @DeleteMapping("/api/pokemons/{id}")
    void deletePokemon(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
