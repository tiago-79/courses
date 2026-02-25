package br.gov.caixa.services;

import br.gov.caixa.model.Course;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CourseService {

    @Transactional
    public Course createCourse(Course course) {
        Log.info("Passing through " + this.getClass().getName() + " with course: " + course.toString());
        course.persist();
        return course;
    }
}
