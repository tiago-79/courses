package br.gov.caixa.resources;

import br.gov.caixa.dto.CourseRequest;
import br.gov.caixa.dto.CourseResponse;
import br.gov.caixa.dto.CreateLessonRequest;
import br.gov.caixa.dto.LessonResponse;
import br.gov.caixa.model.Course;
import br.gov.caixa.model.Lesson;
import br.gov.caixa.services.CourseService;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(CourseService service) {
        this.courseService = service;
    }

    @Inject
    JsonWebToken jwt;

    @Context
    UriInfo uriInfo; // Injeção de contexto para construir URIs dinâmicas

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response createCourse( @Valid CourseRequest courseRequest){
        Log.info("Passing through " + this.getClass().getName());

        Course newCourse = new Course(courseRequest.name());
        this.courseService.createCourse(newCourse);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(newCourse.getId().toString())
                .build();

        CourseResponse payload = new CourseResponse(newCourse.id, newCourse.getName(), List.of());

        return Response.created(uri).entity(payload).build();
    }

    @GET
    public Response getCourses(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {

        List<Course> courses = Course.listAll();
        List<CourseResponse> response = courses
                .stream()
                .map((Course c) -> new CourseResponse(c.id, c.getName(), List.of()))
                .toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response courseById(@PathParam("id") Long id){
        Log.info("Passing through " + this.getClass().getName());
        Course courseById = Course.findById(id);

        if (courseById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new CourseResponse(courseById.id, courseById.getName(), List.of())).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response updateCourse(@PathParam("id") Long id, @Valid CourseRequest courseRequest){
        Log.info("Passing through " + this.getClass().getName());

        Optional <Course> possibleCourse = Course.findByIdOptional(id);

        if (possibleCourse.isEmpty()) {
            Log.info("Course with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build(); // early-return
        }

        Course course = possibleCourse.get();
        course.setName(courseRequest.name());
        URI uri = uriInfo.getAbsolutePath();

        return Response.ok(new CourseResponse(course.id, course.getName(), List.of())).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id){

        Optional<Object> claim = jwt.claim(Claims.sub);
        if (claim.isPresent()){
            System.out.println("sub " + claim.get());
        } else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        //this.courseService.deleteCourse(id);
        Course.deleteById(id);
        return Response.noContent().build();
    }

    // ---------- LESSON ----------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/lessons")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response createLesson(@PathParam("id") Long id, @Valid CreateLessonRequest createLessonRequest) {
        Log.info("Passing through " + this.getClass().getName());

        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Lesson lesson = new Lesson(createLessonRequest.name());
        lesson.persist();
        course.addLesson(lesson);

        URI uri = URI.create("/courses/" + course.id + "/lessons/" + lesson.id);

        LessonResponse lessonResponse = new LessonResponse(lesson.id, lesson.getName());

        return Response.created(uri)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(lessonResponse)
                .build();
    }

    @RolesAllowed("USER")
    @GET
    @Path("/{id}/lessons")
    public Response getLessonsByCourseId(@PathParam("id") Long id){
        Course courseById = Course.findById(id);

        if (courseById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<LessonResponse> response = courseById.getLessons()
                .stream()
                .map((Lesson l) -> new LessonResponse(l.id, l.getName()))
                .toList();

        return Response.ok(response).build();
    }
}
