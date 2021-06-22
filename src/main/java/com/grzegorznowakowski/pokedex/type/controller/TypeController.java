package com.grzegorznowakowski.pokedex.type.controller;

import com.grzegorznowakowski.pokedex.pokemon.controller.PokemonNotFoundException;
import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.type.entity.Type;
import com.grzegorznowakowski.pokedex.type.repository.TypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Grzegorz Nowakowski
 */
@RestController
@RequestMapping("/api")
public class TypeController {

    private final TypeRepository repository;

    TypeController(TypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/types")
    List<Type> all() {
        return repository.findAll();
    }

    @PostMapping("/types")
    Type newType(@RequestBody Type newType) {
        return repository.save(newType);
    }

    @GetMapping("/types/{value}")
    Type getTypeByNameOrId(@PathVariable String value) {

        if(value.matches("\\d*")){
            return repository.findById(Long.valueOf(value)).orElseThrow(() -> new TypeNotFoundException(Long.valueOf(value)));
        } else {
            return repository.findByName(value).orElseThrow(() -> new TypeNotFoundException(value));
        }
    }


    @PutMapping("/types/{id}")
    Type replaceType(@RequestBody Type newType, @PathVariable Long id) {
        return repository.findById(id)
                .map(type -> {
                    type.setName(newType.getName());
                    return repository.save(type);
                })
                .orElseGet(() -> {
                    newType.setId(id);
                    return repository.save(newType);
                });
    }

    @DeleteMapping("/types/{id}")
    void deletePokemon(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
