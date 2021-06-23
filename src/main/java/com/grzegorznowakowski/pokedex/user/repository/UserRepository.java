package com.grzegorznowakowski.pokedex.user.repository;

import com.grzegorznowakowski.pokedex.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Grzegorz Nowakowski
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
    User findByUsername (String name);
}
