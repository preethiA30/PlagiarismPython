package dao;

import model.Assignment;
import model.Course;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/22/18
 * @email thakker.m@husky.neu.edu
 */
public interface CourseDao {

    /**
     * Given the userId in the database
     * gets list of all courses taught by the user
     *
     * @param userId : UserId of the User
     * @return the List of Courses that the user teaches
     */
    List<Course> getCourses(String userId);


    /**
     * Adds a course for the given User
     *
     * @param course:      Given Course of the loggedIn User
     * @param userId:      UserId of the user
     * @param assignments: Assignments for this course on github
     * @returns: the new Course information with the courseId updated
     */
    Course addCourse(Course course, String userId, List<Assignment> assignments);


    /**
     * Given course Id returns the course with the id in the database
     *
     * @param courseId: CourseId in the databse
     * @return: Course with courseid
     */
    Course getCourseByCourseId(String courseId);
}
