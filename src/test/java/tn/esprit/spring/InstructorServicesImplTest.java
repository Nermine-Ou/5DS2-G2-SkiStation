package tn.esprit.spring;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Instructor;

import tn.esprit.spring.services.InstructorServicesImpl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class InstructorServicesImplTest {



    @InjectMocks
    private InstructorServicesImpl instructorServices;




    @Test
    void testAddInstructor() {
        // Define the date format
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Parse the date to LocalDate
        LocalDate dateOfHire = LocalDate.parse("30/09/2021", dateFormat);

        // Create an Instructor instance
        Instructor c = new Instructor();
        c.setFirstName("Salhi");
        c.setLastName("Ahmed");
        c.setDateOfHire(dateOfHire);

        // Add instructor (assuming instructorServices is already instantiated)
        Instructor instructor = instructorServices.addInstructor(c);

        // Logging
        System.out.println("Client " + instructor);

        // Assertions
        assertNotNull(instructor.getFirstName(), "First name should not be null");
        assertNotNull(instructor.getDateOfHire(), "Date of hire should not be null");
        assertTrue(instructor.getNumInstructor() > 0, "Instructor number should be greater than 0");
    }
    @Test
    void testDeleteInstructor() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfHire = LocalDate.parse("30/09/2021", dateFormat);

        // Create and set up a new Instructor instance
        Instructor c = new Instructor();
        c.setFirstName("Salhi");
        c.setLastName("Ahmed");
        c.setDateOfHire(dateOfHire);

        // Add instructor (assuming instructorServices is already instantiated)
        Instructor instructor = instructorServices.addInstructor(c);

        // Assert that the instructor was added successfully
        assertNotNull(instructor, "Instructor should be added successfully");
        assertNotNull(instructor.getNumInstructor(), "Instructor ID should be assigned");

        // Delete the instructor
        instructorServices.deleteInstructor(instructor.getNumInstructor());

        // Verify that the instructor no longer exists (assuming a method like getInstructorById)
        Instructor deletedInstructor = instructorServices.retrieveInstructor(instructor.getNumInstructor());
        assertNull(deletedInstructor, "Instructor should be deleted");
    }

    }
