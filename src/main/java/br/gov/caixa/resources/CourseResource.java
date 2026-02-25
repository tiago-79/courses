package br.gov.caixa.resources;

import br.gov.caixa.dto.CourseDTO;
import br.gov.caixa.dto.LessonDTO;
import br.gov.caixa.model.Course;
import br.gov.caixa.model.Lesson;
import br.gov.caixa.services.CourseService;
import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(CourseService service) {
        this.courseService = service;
    }

    // ---------- COURSE ----------
    @GET
    public Response listAll(){
        Log.info("Passing through " + this.getClass().getName());

        return Response.ok(
                Course.listAll()
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response courseById(@PathParam("id") Long id){
        Log.info("Passing through " + this.getClass().getName());
        Course courseById = Course.findById(id);
        return Response.ok(
                courseById // DTO
        ).build();
    }

    @POST
    public Response createCourse( CourseDTO courseDTO ){
        Log.info("Passing through " + this.getClass().getName());

        this.courseService.createCourse(new Course(courseDTO.name()));
        return Response.status(Response.Status.CREATED).build();
    }

    // ---------- LESSON ----------
    @POST
    @Path("/{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, LessonDTO lessonDTO) {
        Log.info("Passing through " + this.getClass().getName());
        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Lesson lesson = new Lesson(lessonDTO.name());
        lesson.persist();
        course.addLesson(lesson);

        return Response.status(Response.Status.CREATED).build();
    }
}
