package com.grzegorznowakowski.pokedex.pokemonType.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Grzegorz Nowakowski
 */
@Entity
@Table(name="type")
public class PokemonType {

    @Id
    @Column(name="type_id")
    private Long id;

    @Column(name="name", unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
