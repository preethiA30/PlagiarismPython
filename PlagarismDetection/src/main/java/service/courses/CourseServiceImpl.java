package service.courses;

import dao.CourseDao;
import dao.InstructorDao;
import jgit.Jgit;
import jgit.JgitException;
import model.Assignment;
import model.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.assignments.AssignmentService;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/22/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * CourseService : All services related to courses
 */
public class CourseServiceImpl implements CourseService {


    /**
     * Course Dao for Database access
     */
    private CourseDao courseDao;
    /**
     * JgitImpl for accessing git repository
     */
    private Jgit jgit;
    /**
     * Assignment Service for adding assignments
     */
    private AssignmentService assignmentService;

    private static final String delimiter = "/";

    /**
     * Initializing Course Dao for database access
     *
     * @param courseDao: Course Dao for Database access
     */
    public CourseServiceImpl(CourseDao courseDao, Jgit jgitImpl, AssignmentService assignmentService) {
        this.courseDao = courseDao;
        this.jgit = jgitImpl;
        this.assignmentService = assignmentService;
    }

    /**
     * Given the userId in the database
     * gets list of all courses taught by the user
     *
     * @param userId : UserId of the User
     * @return the List of Courses that the user teaches
     */
    @Override
    public List<Course> getCourses(String userId) {
        return courseDao.getCourses(userId);
    }

    /**
     * Will create the course if and only if the repository is a
     * valid repository. This service includes cloning the repository
     * and adding all the current homeworks in the database
     *
     * @param course : Course details such as name, repoUrl
     * @param userId : User adding the course
     * @return The same course with updated CourseId in the database
     */
    @Override
    public Course addCourse(Course course, String userId) throws JgitException {
        String localPath = userId + delimiter + course;
        Course newCourse = null;
        try {
            cloneRepo(course, localPath);
            List<String> homeworks = getAllAssignmentsFromLocal(localPath);
            List<Assignment> assignmentList = assignmentService.addAllAssignments(course.getHexid(), homeworks);
            courseDao.addCourse(course, userId, assignmentList);
            deleteRepo(localPath);
        } catch (JgitException jgitE) {
            logger.error("Error accessing Jgit Repo" + jgitE);
            throw new JgitException("Error in accessing repo");
        }
        return newCourse;
    }

    /**
     * Deletes all the local files
     *
     * @param localPath: LocalPath where all the git files are stored
     *
     * @return if successfully deleted returns true else returns false
     */
    private boolean deleteRepo(String localPath) {
        try {
            File file = new File(localPath);
            return file.delete();
        }
        catch (Exception exp) {
            logger.error("Exception in deleting the files" + exp);
        }
        return false;
    }


    /**
     * Given courseId returns the course
     *
     * @param courseId : Course Id for which the course needs to be returned
     * @return The course with courseId
     */
    @Override
    public Course getCourseById(String courseId) {
        return courseDao.getCourseByCourseId(courseId);
    }


    /**
     * Helper function: Scans the localPath where the git repository is cloned
     * and gets a list of assignmentsName in the repository
     *
     * @param localPath: Local File Path
     * @return the list of File Name in the local path
     */
    private List<String> getAllAssignmentsFromLocal(String localPath) {
        List<String> assignments = new LinkedList<>();
        try {
            File file = new File(localPath);
            File[] listOfFile = file.listFiles();
            for (int i = 0; i < listOfFile.length; i++) {
                if (listOfFile[i].isDirectory()&&!listOfFile[i].isHidden())
                    assignments.add(listOfFile[i].getName());
            }
        } catch (Exception exp) {
            logger.error("Error in getAllAssignmentsFromLocal" + exp);
        }
        return assignments;
    }


    /**
     * Clones repository for the user in the localPAth mentioned or configured
     *
     * @param course:    Course for which the cloning is to be performed
     * @param localPath: Local path on the local file system on the server
     * @throws JgitException if api has issues or unable to login to git due to invalid credentials
     */
    private void cloneRepo(Course course, String localPath) throws JgitException {
        jgit.cloneRepo(course.getRepoUrl(), localPath);
    }

    /**
     * Logger
     */
    private static Logger logger = LogManager.getLogger();
}
