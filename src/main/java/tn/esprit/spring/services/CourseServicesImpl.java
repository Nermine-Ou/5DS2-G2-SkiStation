package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CourseServicesImpl implements ICourseServices {

    // Initialize the logger
    private static final Logger logger = LogManager.getLogger(CourseServicesImpl.class);

    private ICourseRepository courseRepository;

    @Override
    public List<Course> retrieveAllCourses() {
        logger.info("Retrieving all courses");
        List<Course> courses = courseRepository.findAll();
        logger.debug("Number of courses retrieved: {}", courses.size());
        return courses;
    }

    @Override
    public Course addCourse(Course course) {
        logger.info("Adding a new course: {}", course);
        Course savedCourse = courseRepository.save(course);
        logger.debug("Course added with ID: {}", savedCourse.getNumCourse());
        return savedCourse;
    }

    @Override
    public Course updateCourse(Course course) {
        logger.info("Updating course with ID: {}", course.getNumCourse());
        Course updatedCourse = courseRepository.save(course);
        logger.debug("Course updated: {}", updatedCourse);
        return updatedCourse;
    }

    @Override
    public Course retrieveCourse(Long numCourse) {
        logger.info("Retrieving course with ID: {}", numCourse);
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course != null) {
            logger.debug("Course found: {}", course);
        } else {
            logger.warn("No course found with ID: {}", numCourse);
        }
        return course;
    }
}
