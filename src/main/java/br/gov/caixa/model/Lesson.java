package br.gov.caixa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Lesson extends PanacheEntity {

    @Column(unique = true, nullable = false)
    private String name;

    public Lesson( String name) {
        this.name = name;
    }

    public Lesson() {
    }

    public String getName() {
        return name;
    }
}
