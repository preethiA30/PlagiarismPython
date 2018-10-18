package controller;

import jgit.JgitException;
import model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.courses.CourseServiceImpl;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Router for Controller
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CourseController {


    /**
     * CourseServices
     */
    private CourseServiceImpl courseService;


    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    /**
     * Gets course for given UserID
     *
     * @param userId: UserId in the database
     * @return the List of CourseService for the given User
     */
    @RequestMapping(value = "/api/user/{userId}/course", method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getCourseForUser(@PathVariable String userId) {
        try {
            List<Course> courseList = courseService.getCourses(userId);
            return new ResponseEntity(courseList, HttpStatus.ACCEPTED);
        } catch (Exception exp) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Creates a new course for the User if and only if
     * the git url is a valid one and is accessible by Id and password
     *
     * @param courseDetails: courseDetails like coursename, git url
     * @return the response with the  Course if it has been created else returns PARtiALcontent response error
     */
    @RequestMapping(value = "/api/user/{userId}/course", method = RequestMethod.POST)
    public ResponseEntity<Course> newCourse(@PathVariable String userId, @RequestBody Course courseDetails) {
        try {
            return new ResponseEntity<>(courseService.addCourse(courseDetails, userId), HttpStatus.ACCEPTED);
        } catch (JgitException e) {
            logger.error("Error in adding a new course");
            return new ResponseEntity<>(courseDetails, HttpStatus.PARTIAL_CONTENT);
        }
    }

    /**
     * logger
     */
    private static Logger logger = LogManager.getLogger();


}
