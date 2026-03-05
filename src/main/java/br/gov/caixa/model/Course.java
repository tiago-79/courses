package br.gov.caixa.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany
    private final List<Lesson> lessons = new ArrayList<>();

    protected Course() {
    }

    public Course( String name ) {
        this.name = name;
    }

    public void addLesson( Lesson lesson ) {

        Lesson validatedLesson = Objects.requireNonNull(lesson, "lesson must no be null");
        this.lessons.add(validatedLesson);
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
        return Collections.unmodifiableList(this.lessons);
    }

    public void changeName(String newName) {
        this.name = Objects.requireNonNull(newName, "name must not be null");
    }
}
