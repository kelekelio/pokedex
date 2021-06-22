package com.grzegorznowakowski.pokedex.type.repository;

import com.grzegorznowakowski.pokedex.type.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByName(String name);
}
