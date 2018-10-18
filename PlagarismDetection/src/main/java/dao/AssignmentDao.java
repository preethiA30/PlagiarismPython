package dao;

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
 * Represents the database operations for Collection Assignement
 */
public interface AssignmentDao {


    /**
     * Adds all homeworks with new ids and returns the list of homework
     * with updated ids in the database
     *
     * @param courseId:      CourseId for which homework needs to be added
     * @param homeworkNames: List of all the homework Names in the course
     * @return List of Homeworks with updated ids
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
     * @param results    :    List of results
     * @param homeworkId : Homework id
     * @return List of results with their updated ids
     */
    List<Result> storeResults(List<Result> results, String homeworkId);


    /**
     * Given the assignmentId
     * stores the results in the database
     * and returns the List of the results with theirs  ids in the database
     *
     * @param assignmentId :   assignmentId of the Assignment to get results
     * @return List of results stored in the database
     */
    List<Result> getResults(String assignmentId);


    /**
     * Given a resultId of the resultCollection
     * @param resultId: resultId of the result database
     * @return Result model specifying all relevant information
     */
    Result getResultByResultId(String resultId);


    /**
     * Deletes Results for a strategy type
     * @param assignmentId: AssignmentId in the mongo assignment collection
     * @param strategy: Strategy type for which the results are to be deleted
     * @return true if successfully deleted else return false
     */
    boolean deleteResultsForStrategyType(String assignmentId, String strategy);


}
