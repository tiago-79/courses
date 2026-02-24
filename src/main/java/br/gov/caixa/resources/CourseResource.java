package br.gov.caixa.resources;

import br.gov.caixa.model.Course;
import br.gov.caixa.services.CourseService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    private final CourseService service;

    public CourseResource( CourseService service ) {
        this.service = service;
    }

    @POST
    public Response createCourse(CourseDTO courseDTO ){
        this.service.createCourse(new Course(courseDTO.name()));
        return Response.status(201).build();
    }
}
