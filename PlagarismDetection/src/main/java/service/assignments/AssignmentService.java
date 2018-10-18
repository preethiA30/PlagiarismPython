package service.assignments;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/23/18
 * @email thakker.m@husky.neu.edu
 */

import model.Assignment;
import model.Result;

import java.util.List;

/**
 * Specification for all homework Services
 */
public interface AssignmentService {


    /**
     * @param courseId:      The Course Id in the database
     * @param homeworkNames: list of all homework names which are nothing but folder names
     * @return List of Assignements with their ids updated as in the database
     */
    List<Assignment> addAllAssignments(String courseId, List<String> homeworkNames);


    /**
     * Given courseId returns the list of assignments for the same
     *
     * @param courseId: courseId in the database
     * @return List of assignment model
     */
    List<Assignment> getAssignment(String courseId);


    /**
     * Given the homeworkId and the plagarism results
     * stores the results in the database
     * and returns the List of the results with theirs updated ids in the database
     *
     * @param results:    List of results
     * @param homeworkId: Homework id
     * @return List of results with their updated ids
     */
    List<Result> storeResults(List<Result> results, String homeworkId);


    /**
     * Given the homeworkId
     * stores the results in the database
     * and returns the List of the results with theirs  ids in the database
     *
     * @param assignmentId :   assignmentId of the Assignment to get results
     * @return List of results stored in the database
     */
    List<Result> getResults(String assignmentId);


    /**
     * Returns the ResultId by the Id given
     *
     * @param resultId: Result Id for which result is required
     * @return Result with resultId
     */
    Result getResultByResultId(String resultId);

    /**
     *  Deletes Results for a specific strategy for a sepcific homework so as new results can he
     *  generated.
     * @param assignmentId: AssignmentId for which the results are to be deleted
     * @param strategy: Starategy for which the results are to delted
     * @return true if the results are deleted else false otherwise
     */
    boolean deleteResultsForStrategyType(String assignmentId, String strategy);


}
