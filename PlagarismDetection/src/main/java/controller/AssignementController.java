package controller;

import jgit.Jgit;
import model.Assignment;
import model.Course;
import model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.plagiarism.PlagiarismResult;
import service.plagiarism.Strategy;
import service.assignments.AssignmentService;
import service.counter.CounterServiceImpl;
import service.courses.CourseService;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Controller to map all requests on assignment page
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AssignementController {

    private static final String delimiter = "/";

    /**
     * Services for all assignment
     */
    private AssignmentService assignmentService;


    /**
     * Plagarism algorithm
     */
    private PlagiarismResult plagiarismResultInterface;

    /**
     * Jgit Service
     */
    private Jgit jgit;

    /**
     * Course Service for all service related to courses
     */
    private CourseService courseService;

    /**
     * Counter Service for all service related to courses
     */
    private CounterServiceImpl counterService;

    /**
     * Constructor to initialize assignmentService
     *
     * @param assignmentService:         Services for all assignment
     * @param plagiarismResultInterface: Plagiarism service
     */
    public AssignementController(AssignmentService assignmentService, PlagiarismResult plagiarismResultInterface, Jgit jgit, CourseService courseService, CounterServiceImpl counterService) {
        this.assignmentService = assignmentService;
        this.plagiarismResultInterface = plagiarismResultInterface;
        this.jgit = jgit;
        this.courseService = courseService;
        this.counterService = counterService;
    }


    /**
     * Given a course id fetches all the CourseIds
     *
     * @param courseId: Course Id for which assignments are required
     * @return List of all assignments in the course
     */
    // modify the url in frontend and backend works for me in the backend
    @RequestMapping(value = "/api/course/{courseId}/assignment", method = RequestMethod.GET)
    public ResponseEntity<List<Assignment>> getAssignments(@PathVariable String courseId) {
        List allAssignments = null;
        try {
            return new ResponseEntity(assignmentService.getAssignment(courseId), HttpStatus.ACCEPTED);
        } catch (Exception exp) {
            logger.error("Error in service getAssignments" + courseId);
            return new ResponseEntity(allAssignments, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Generate results for the given assignment, course, user
     *
     * @param userId:             UserId of the current user logged in the user
     * @param courseId:           Course Id for which the homework belongs
     * @param assignmentName:     Name of the assignment
     * @param assignmentId:       Id of the assignment in the databse
     * @param comparisonStrategy: Comparision strategy to be used to compare the file system
     * @return a response with assignment with OK if accepted else Internal Server error
     */
    @RequestMapping(value = "/api/user/{userId}/course/{courseId}/assignment/{assignmentName}/{assignmentId}/{comparisonStrategy}", method = RequestMethod.GET)
    public ResponseEntity<List<Assignment>> generateResults(@PathVariable String userId,
                                                            @PathVariable String courseId,
                                                            @PathVariable String assignmentName,
                                                            @PathVariable String assignmentId,
                                                            @PathVariable String comparisonStrategy) {
        List allAssignments = new LinkedList();
        try {
            // Build a unique local path to clone repo
            String localPath = userId+Math.random() + delimiter + courseId;
            // get the clone url
            Course course = courseService.getCourseById(courseId);
            String repoUrlForCourse = course.getRepoUrl();
            // clone the repo
            jgit.cloneRepo(repoUrlForCourse, localPath);

            // Convert string to enum
            Strategy strategy = Strategy.toStrategyType(comparisonStrategy);

            // delete all the result for the specific strategy for the specific assigment
            assignmentService.deleteResultsForStrategyType(assignmentId,comparisonStrategy);
            // generate results
            List<Result> results = plagiarismResultInterface.generateResults(localPath + "/" + assignmentName + "/", strategy, 100, counterService);

            // store results in DB
            assignmentService.storeResults(results, assignmentId);

            // Delete the local files
            deleteRepo(localPath);

            return new ResponseEntity(assignmentService.getAssignment(courseId), HttpStatus.ACCEPTED);
        } catch (Exception exp) {
            logger.error("Error in service generateResults" + exp);
            return new ResponseEntity(allAssignments, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Given Local path deletes all files from the localPAth
     *
     * @param localPath: Local path
     * @return true if successfully deleted repo else false
     */
    public boolean deleteRepo(String localPath) {
        try {
            FileUtils.deleteDirectory(new File(localPath));
            return true;
        } catch (Exception exp) {
            logger.error("Exception in deleting the files" + exp);
        }
        return false;
    }


    /**
     * Logger
     */
    private static Logger logger = LogManager.getLogger();


}
