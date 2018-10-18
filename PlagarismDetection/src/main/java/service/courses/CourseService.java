package service.courses;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/22/18
 * @email thakker.m@husky.neu.edu
 */

import jgit.JgitException;
import model.Course;

import java.util.List;

/**
 * Specification for all the Courses service to be devloped
 */
public interface CourseService {

    /**
     * Given the userId in the database
     * gets list of all courses taught by the user
     *
     * @param userId: UserId of the User
     * @return the List of Courses that the user teaches
     */
    List<Course> getCourses(String userId);


    /**
     * Will create the course if and only if the repository is a
     * valid repository. This service includes cloning the repository
     * and adding all the current homeworks in the database
     *
     * @param course: Course details such as name, repoUrl
     * @param userId: User adding the course
     * @return The same course with updated CourseId
     */
    Course addCourse(Course course, String userId) throws JgitException;


    /**
     * Given courseId returns the course
     *
     * @param courseId: Course Id for whcihc the courser needs to be returnds
     * @return The course with courseId
     */

    Course getCourseById(String courseId);


}
