package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInstructor() {
        Instructor instructor = new Instructor(); // configure your Instructor object
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructor(instructor);

        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveAllInstructors() {
        List<Instructor> instructors = List.of(new Instructor(), new Instructor());
        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> result = instructorServices.retrieveAllInstructors();

        assertEquals(2, result.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor(); // configure your Instructor object
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.updateInstructor(instructor);

        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        Long numInstructor = 1L;
        Instructor instructor = new Instructor(); // configure your Instructor object
        when(instructorRepository.findById(numInstructor)).thenReturn(Optional.of(instructor));

        Instructor result = instructorServices.retrieveInstructor(numInstructor);

        assertNotNull(result);
        verify(instructorRepository, times(1)).findById(numInstructor);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        Long numCourse = 1L;
        Instructor instructor = new Instructor(); // configure your Instructor object
        Course course = new Course(); // configure your Course object
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, numCourse);

        assertNotNull(result);
        assertEquals(1, instructor.getCourses().size());
        verify(courseRepository, times(1)).findById(numCourse);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
