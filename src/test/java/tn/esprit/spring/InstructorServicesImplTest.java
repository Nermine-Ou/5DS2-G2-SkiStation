package tn.esprit.spring;

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
}
