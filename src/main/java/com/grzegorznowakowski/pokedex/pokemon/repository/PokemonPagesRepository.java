package com.grzegorznowakowski.pokedex.pokemon.repository;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface PokemonPagesRepository extends PagingAndSortingRepository<PokemonEntity, Integer> {

    @Query(value = "select * from pokemon p inner join pokemon_type pt on p.id = pt.pokemon_id inner join type t on pt.type_id = t.type_id where t.name = :typeName",
            nativeQuery = true)
    Page<PokemonEntity> findByType(@Param("typeName")String name, Pageable pageable);

}
