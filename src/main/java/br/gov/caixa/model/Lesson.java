package br.gov.caixa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Lesson extends PanacheEntity {

    @Column(unique = true, nullable = false)
    @NotNull
    @NotBlank
    //@Size(min = 3, message = "O nome da aula deve ter no mínimo 3 caracteres.")
    private String name;

    public Lesson( String name) {
        this.name = name;
    }

    protected Lesson() {
    }

    public String getName() {
        return name;
    }
}
