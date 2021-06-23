package com.grzegorznowakowski.pokedex.pokemon.repository;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface PokemonPagesRepository extends PagingAndSortingRepository<PokemonEntity, Integer> {
}
