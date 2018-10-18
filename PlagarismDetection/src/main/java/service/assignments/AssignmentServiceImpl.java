package service.assignments;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/23/18
 * @email thakker.m@husky.neu.edu
 */

import dao.AssignmentDao;
import model.Assignment;
import model.Result;

import java.util.List;

/**
 * HomeService for all operations related to homework
 */
public class AssignmentServiceImpl implements AssignmentService {


    /**
     * Assignment Dao for datacess
     */
    private AssignmentDao assignmentDao;


    /**
     * Constructor to initialize assignment database access
     *
     * @param assignmentDao :assignement database access
     */
    public AssignmentServiceImpl(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    /**
     * @param courseId      :      The Course Id of course in the database
     * @param homeworkNames : list of all homework names which are nothing but folder names
     * @return List of Assignements with their ids updated
     */
    @Override
    public List<Assignment> addAllAssignments(String courseId, List<String> homeworkNames) {
        return assignmentDao.addAllAssignments(courseId, homeworkNames);
    }

    /**
     * Given courseId returns the list of assignments for the same
     *
     * @param courseId : courseId in the database
     * @return List of assignment model
     */
    @Override
    public List<Assignment> getAssignment(String courseId) {
        return assignmentDao.getAssignment(courseId);
    }

    /**
     * Given the homeworkId and the plagarism results
     * stores the results in the database
     * and returns the List of the results with theirs updated ids in the database
     *
     * @param results    :    List of results
     * @param homeworkId : Homework id
     * @return List of results with their updated ids
     */
    @Override
    public List<Result> storeResults(List<Result> results, String homeworkId) {
        return assignmentDao.storeResults(results, homeworkId);
    }

    /**
     * Given the homeworkId
     * stores the results in the database
     * and returns the List of the results with theirs  ids in the database
     *
     * @param assignmentId :   assignmentId of the Assignment to get results
     * @return List of results stored in the database
     */
    @Override
    public List<Result> getResults(String assignmentId) {
        return assignmentDao.getResults(assignmentId);
    }


    /**
     * Gets the result by the given resultId
     *
     * @param resultId: Result Id for which result is required
     * @return Result with the given resultId
     */
    @Override
    public Result getResultByResultId(String resultId) {
        return assignmentDao.getResultByResultId(resultId);
    }


    /**
     * Deletes Results for a specific strategy for a sepcific homework so as new results can he
     * generated.
     *
     * @param assignmentId: AssignmentId for which the results are to be deleted
     * @param strategy:     Starategy for which the results are to delted
     * @return true if the results are deleted else false otherwise
     */
    public boolean deleteResultsForStrategyType(String assignmentId, String strategy) {
        return assignmentDao.deleteResultsForStrategyType(assignmentId, strategy);
    }


}
