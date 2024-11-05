package tn.esprit.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.services.IInstructorServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)



@Slf4j
@SpringBootTest
class InstructorServicesImplTest {



    @Autowired

    IInstructorServices instructorServices;





    @Test

    public void testAddClient() throws ParseException {



        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfHire = LocalDate.parse("30/09/2021", dateFormat);

        // Create an Instructor instance without numInstructor
        Instructor c = new Instructor();
        c.setFirstName("Salhi");
        c.setLastName("Ahmed");
        c.setDateOfHire(dateOfHire);

        // Add instructor (assuming instructorServices is already instantiated)
        Instructor instructor = instructorServices.addInstructor(c);

        Instructor client = instructorServices.addInstructor(c);

        log.info("client "+client);

        assertNotNull(client.getNumInstructor());

        assertNotNull(client.getFirstName());

        assertTrue(client.getLastName().length() > 0);



    }
    @Test
    public void testDeleteInstructor() {
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
    @Test
    public void testModifyInstructor() {
        // Set up date formatter and parse the hire date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfHire = LocalDate.parse("30/09/2021", dateFormat);

        // Create and set up a new Instructor instance
        Instructor c = new Instructor();
        c.setFirstName("Salhi");
        c.setLastName("Ahmed");
        c.setDateOfHire(dateOfHire);

        // Add the instructor to the database
        Instructor instructor = instructorServices.addInstructor(c);

        // Assert that the instructor was added successfully
        assertNotNull(instructor, "Instructor should be added successfully");
        assertNotNull(instructor.getNumInstructor(), "Instructor ID should be assigned");

        // Modify the instructor's details
        instructor.setFirstName("Amine");
        instructor.setLastName("Mahdi");
        instructor.setDateOfHire(LocalDate.parse("01/10/2022", dateFormat));

        // Call the updateInstructor method to save the changes
        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        // Assert that the updated instructor is not null and that the changes were saved
        assertNotNull(updatedInstructor, "Updated instructor should not be null");
        assertEquals("Amine", updatedInstructor.getFirstName(), "First name should be updated");
        assertEquals("Mahdi", updatedInstructor.getLastName(), "Last name should be updated");
        assertEquals(LocalDate.parse("01/10/2022", dateFormat), updatedInstructor.getDateOfHire(), "Hire date should be updated");

        // Retrieve the updated instructor and verify the changes are persisted in the database
        Instructor retrievedInstructor = instructorServices.retrieveInstructor(updatedInstructor.getNumInstructor());
        assertNotNull(retrievedInstructor, "Retrieved instructor should not be null");
        assertEquals("Amine", retrievedInstructor.getFirstName(), "First name should match the updated value");
        assertEquals("Mahdi", retrievedInstructor.getLastName(), "Last name should match the updated value");
        assertEquals(LocalDate.parse("01/10/2022", dateFormat), retrievedInstructor.getDateOfHire(), "Hire date should match the updated value");
    }
    @Test
    public void testRetrieveInstructor() {
        // Set up date formatter and parse the hire date
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateOfHire = LocalDate.parse("15/08/2020", dateFormat);

        // Create and set up a new Instructor instance
        Instructor c = new Instructor();
        c.setFirstName("John");
        c.setLastName("Doe");
        c.setDateOfHire(dateOfHire);

        // Add the instructor to the database
        Instructor addedInstructor = instructorServices.addInstructor(c);

        // Assert that the instructor was added successfully
        assertNotNull(addedInstructor, "Instructor should be added successfully");
        assertNotNull(addedInstructor.getNumInstructor(), "Instructor ID should be assigned");

        // Retrieve the instructor by their ID
        Instructor retrievedInstructor = instructorServices.retrieveInstructor(addedInstructor.getNumInstructor());

        // Assert that the retrieved instructor is not null
        assertNotNull(retrievedInstructor, "Instructor should be retrieved successfully");

        // Assert that the retrieved instructor's details match the added one
        assertEquals(addedInstructor.getNumInstructor(), retrievedInstructor.getNumInstructor(), "Instructor ID should match");
        assertEquals("John", retrievedInstructor.getFirstName(), "First name should match");
        assertEquals("Doe", retrievedInstructor.getLastName(), "Last name should match");
        assertEquals(LocalDate.parse("15/08/2020", dateFormat), retrievedInstructor.getDateOfHire(), "Hire date should match");
    }



}