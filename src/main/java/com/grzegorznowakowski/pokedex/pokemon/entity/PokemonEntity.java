package com.grzegorznowakowski.pokedex.pokemon.entity;

import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author Grzegorz Nowakowski
 */

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="pokemon")
public class PokemonEntity {

    @Id
    @Column
    private Integer id;

    @Column(unique=true)
    private String name;

    @ManyToMany
    @JoinTable(
            name="pokemon_type",
            joinColumns=@JoinColumn(name="pokemon_id"),
            inverseJoinColumns=@JoinColumn(name="type_id")
    )
    private Set<PokemonType> types;






    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(Set<PokemonType> types) {
        this.types = types;
    }
}
