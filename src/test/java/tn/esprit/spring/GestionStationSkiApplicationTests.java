/*package tn.esprit.spring;

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
class GestionStationSkiApplicationTests {



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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestionStationSkiApplicationTests {

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
		// Set up sample instructor and course objects
		instructor = new Instructor(1L, "Jane", "Doe", LocalDate.of(2020, 1, 1), new HashSet<>());
		course = new Course();
		course.setNumCourse(1L);
	}

	@Test
	void addInstructor() {
		when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

		Instructor savedInstructor = instructorServices.addInstructor(instructor);

		assertNotNull(savedInstructor);
		assertEquals(instructor.getNumInstructor(), savedInstructor.getNumInstructor());
		verify(instructorRepository, times(1)).save(instructor);
	}

	@Test
	void retrieveAllInstructors() {
		List<Instructor> instructorList = List.of(instructor);
		when(instructorRepository.findAll()).thenReturn(instructorList);

		List<Instructor> result = instructorServices.retrieveAllInstructors();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(instructorRepository, times(1)).findAll();
	}

	@Test
	void updateInstructor() {
		when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

		Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

		assertNotNull(updatedInstructor);
		assertEquals(instructor.getFirstName(), updatedInstructor.getFirstName());
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
		Set<Course> assignedCourses = result.getCourses();
		assertEquals(1, assignedCourses.size());
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




