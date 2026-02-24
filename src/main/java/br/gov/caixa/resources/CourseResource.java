package br.gov.caixa.resources;

import br.gov.caixa.model.Course;
import br.gov.caixa.services.CourseService;

public class CourseResource {

    private final CourseService service;

    public CourseResource(CourseService service) {
        this.service = service;
    }

    public void createCourse(CourseDTO courseDTO){
        Course course = this.service.createCourse(new Course(courseDTO.name()));
    }
}
