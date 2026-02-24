package br.gov.caixa.resources;

import br.gov.caixa.model.Course;
import br.gov.caixa.services.CourseService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/courses")
public class CourseResource {

    private final CourseService service;

    public CourseResource( CourseService service ) {
        this.service = service;
    }

    @POST
    public void createCourse( CourseDTO courseDTO ){
        Course course = this.service.createCourse(new Course(courseDTO.name()));
    }
}
