package com.grzegorznowakowski.pokedex.pokemon.controller;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonRepository;
import com.grzegorznowakowski.pokedex.type.controller.TypeNotFoundException;
import com.grzegorznowakowski.pokedex.type.entity.Type;
import com.grzegorznowakowski.pokedex.type.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
public class PokemonController {

    private final PokemonRepository repository;

    @Autowired
    private TypeRepository typeRepository;


    PokemonController(PokemonRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/api/pokemons")
    List<PokemonEntity> all() {
        return repository.findAll();
    }

    @GetMapping("/api/pokemons/type/{type}")
    List<PokemonEntity> listAllByType(@PathVariable String type) {
        return repository.findByType(type);
    }


    @PostMapping("/api/pokemons")
    PokemonEntity newPokemon(@RequestBody PokemonEntity newPokemon) {

        List<Type> allTypes = typeRepository.findAll();

        for (Type pokeType: newPokemon.getTypes()) {
            if (allTypes.stream().noneMatch(o -> o.getId().equals(pokeType.getId()))) {
                typeRepository.save(pokeType);
            }
        }

        return repository.save(newPokemon);
    }

    @GetMapping("/api/pokemons/{value}")
    PokemonEntity findById(@PathVariable String value) {

        if(value.matches("\\d*")){
            return repository.findById(Integer.valueOf(value)).orElseThrow(() -> new PokemonNotFoundException(Integer.valueOf(value)));
        } else {
            return repository.findByName(value).orElseThrow(() -> new PokemonNotFoundException(value));
        }
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
