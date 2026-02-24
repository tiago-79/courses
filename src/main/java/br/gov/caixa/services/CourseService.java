package br.gov.caixa.services;

import br.gov.caixa.model.Course;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourseService {

    public Course createCourse(Course course) {
        return course;
    }
}
