package com.grzegorznowakowski.pokedex.pokemon.repository;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Integer> {

    PokemonEntity findByName(String name);

}
