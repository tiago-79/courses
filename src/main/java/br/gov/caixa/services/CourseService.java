package br.gov.caixa.services;

import br.gov.caixa.dto.CourseDTO;
import br.gov.caixa.model.Course;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class CourseService {

    @PostConstruct
    void init() {
        Log.info("Inicializando agora...");
    }

    @Transactional
    public Course createCourse(Course course) {
        Log.info("Passing through " + this.getClass().getName() + " with course: " + course.toString());
        course.persist();
        return course;
    }

    public List<Course> findAllPaginado(int pageIndex, int pageSize) {
        return Course.findAll()                   // Cria a query
                .page(pageIndex, pageSize)   // Define a página e o tamanho
                .list();                     // Executa a busca
    }

    public Course getCourse(Long id){
        return (Course) Course.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Course not found..."));
    }

    @Transactional
    public Course updateCourse(Long id, String name) {
        Course course = getCourse(id);

        course.setName(name);
        return course;
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourse(id);
        course.delete();
    }
}
