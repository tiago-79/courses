package br.gov.caixa.model;


import java.util.ArrayList;
import java.util.List;


public class Course  {

    private Long id;
    private String name;
    private List<Lesson> lessons;

    public Course(String name ) {
        this.name = name;
        this.lessons = new ArrayList<>();
    }
}
