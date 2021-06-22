package com.grzegorznowakowski.pokedex.pokemon.entity;

import com.grzegorznowakowski.pokedex.type.entity.Type;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author Grzegorz Nowakowski
 */
@Entity
@Table(name ="pokemon")
public class PokemonEntity {

    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name="pokemon_type",
            joinColumns=@JoinColumn(name="pokemon_id"),
            inverseJoinColumns=@JoinColumn(name="type_id")
    )
    private Set<Type> types;






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

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }
}
