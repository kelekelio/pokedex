package com.grzegorznowakowski.pokedex.pokemonType.repository;

import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonTypePagesRepository  extends PagingAndSortingRepository<PokemonType, Long> {

}