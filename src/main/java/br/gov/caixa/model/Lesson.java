package br.gov.caixa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "lessons")
public class Lesson extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    protected Lesson() {
    }

    public Lesson( String name) { this.name = name;  }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
