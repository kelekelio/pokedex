package com.grzegorznowakowski.pokedex.type.entity;

import javax.persistence.*;

/**
 * @author Grzegorz Nowakowski
 */
@Entity
@Table(name="type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="type_id")
    private Long id;

    @Column(name="name")
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
