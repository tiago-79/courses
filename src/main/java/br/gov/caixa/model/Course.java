package br.gov.caixa.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
@Consumes({"application/json"})
@Produces(MediaType.APPLICATION_JSON)
public class Course extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 3, message = "O nome do curso deve ter no mínimo 3 caracteres.")
    private String name;

    @OneToMany
    private List<Lesson> lessons;

    protected Course() {
    }

    public Course( String name ) {
        this.name = name;
        this.lessons = new ArrayList<>();
    }

    public void addLesson( Lesson lesson ) {
        this.lessons.add(lesson);
    }

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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
