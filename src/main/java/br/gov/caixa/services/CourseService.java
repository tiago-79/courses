package br.gov.caixa.services;

import br.gov.caixa.model.Course;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CourseService {

    @Transactional
    public Course createCourse(Course course) {

        course.persist();
        return course;
    }
}
