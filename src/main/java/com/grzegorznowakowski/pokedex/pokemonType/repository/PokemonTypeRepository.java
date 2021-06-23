package com.grzegorznowakowski.pokedex.pokemonType.repository;

import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {
    Optional<PokemonType> findByName(String name);
}
