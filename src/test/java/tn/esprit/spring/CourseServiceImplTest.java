package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseServicesImplTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllCourses_ShouldReturnCourseList() {
        Course course1 = new Course(/* initialize with sample data */);
        Course course2 = new Course(/* initialize with sample data */);
        List<Course> courseList = Arrays.asList(course1, course2);

        when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> result = courseServices.retrieveAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void addCourse_ShouldSaveAndReturnCourse() {
        Course course = new Course(/* initialize with sample data */);

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.addCourse(course);

        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse_ShouldUpdateAndReturnCourse() {
        Course course = new Course(/* initialize with sample data */);

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.updateCourse(course);

        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void retrieveCourse_ShouldReturnCourse_WhenCourseExists() {
        Long courseId = 1L;
        Course course = new Course(/* initialize with sample data */);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseServices.retrieveCourse(courseId);

        assertNotNull(result);
        assertEquals(course, result);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void retrieveCourse_ShouldReturnNull_WhenCourseDoesNotExist() {
        Long courseId = 1L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Course result = courseServices.retrieveCourse(courseId);

        assertEquals(null, result);
        verify(courseRepository, times(1)).findById(courseId);
    }
}
