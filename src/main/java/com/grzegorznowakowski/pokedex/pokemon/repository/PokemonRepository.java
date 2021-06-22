package com.grzegorznowakowski.pokedex.pokemon.repository;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntity, Integer> {

    Optional<PokemonEntity> findByName(String name);

    @Query(value = "select * from pokemon p inner join pokemon_type pt on p.id = pt.pokemon_id inner join type t on pt.type_id = t.type_id where t.name = :typeName",
            nativeQuery = true)
    List<PokemonEntity> findByType(@Param("typeName")String name);

}
