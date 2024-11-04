/*package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInstructor() {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setFirstName("Salhi");
        instructor.setLastName("Ahmed");

        // Mocking repository save method
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        // Assert
        assertNotNull(savedInstructor);
        assertEquals("Salhi", savedInstructor.getFirstName());
    }

    @Test
    public void testDeleteInstructor() {
        // Arrange
        Long instructorId = 1L;

        // Mocking repository behavior for delete
        doNothing().when(instructorRepository).deleteById(instructorId);

        // Act
        instructorServices.deleteInstructor(instructorId);

        // Assert
        verify(instructorRepository, times(1)).deleteById(instructorId);
    }
}*/
package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructor = new Instructor(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        course = new Course();
        course.setNumCourse(1L);
    }

    @Test
    void addInstructor() {
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor result = instructorServices.addInstructor(instructor);

        assertNotNull(result);
        assertEquals(instructor.getNumInstructor(), result.getNumInstructor());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveAllInstructors() {
        List<Instructor> instructors = Arrays.asList(instructor);
        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> result = instructorServices.retrieveAllInstructors();

        assertEquals(1, result.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void updateInstructor() {
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor result = instructorServices.updateInstructor(instructor);

        assertEquals(instructor.getFirstName(), result.getFirstName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveInstructor() {
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));

        Instructor result = instructorServices.retrieveInstructor(1L);

        assertNotNull(result);
        assertEquals(instructor.getNumInstructor(), result.getNumInstructor());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void addInstructorAndAssignToCourse() {
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNotNull(result);
        assertEquals(1, result.getCourses().size());
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void deleteInstructor() {
        doNothing().when(instructorRepository).deleteById(anyLong());

        instructorServices.deleteInstructor(1L);

        verify(instructorRepository, times(1)).deleteById(1L);
    }
}

