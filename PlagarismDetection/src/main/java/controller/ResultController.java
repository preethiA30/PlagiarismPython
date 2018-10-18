package controller;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

import com.google.gson.Gson;
import model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.assignments.AssignmentService;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the Results Controller
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ResultController {


    /**
     * Assignments service
     */
    private AssignmentService assignmentService;


    /**
     * Initializes the sercvice
     *
     * @param assignmentService: Assignment Service
     */
    public ResultController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * gets the List of results from the AssignmentId
     *
     * @param assignmentId: Assignment id
     * @returns list of results for the assignment id if generated
     */
    @RequestMapping(value = "/api/assignment/{assignmentId}/result", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getResultsForAssignementId(@PathVariable String assignmentId) {

        try {
            Gson gson = new Gson();
            List<Result> listAssignemnt = assignmentService.getResults(assignmentId);
            List<String> jsonResults = new LinkedList<>();
            for (Result result : listAssignemnt) {
                String jsonResult = gson.toJson(result);
                jsonResults.add(jsonResult);

            }
            return new ResponseEntity(jsonResults, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            logger.error("Error geting results for Id");
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Gets all the result by resultId
     * @param resultId: Id of the result in the system database
     * @return Result model with resultId as given Id in the database
     */
    @RequestMapping(value = "/api/assignment/result/{resultId}", method = RequestMethod.GET)
    public ResponseEntity<Result> getResultByResultId(@PathVariable String resultId) {
        try {
            Result result=assignmentService.getResultByResultId(resultId);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            logger.error("Error in Getting results By Id" + e);
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * logger
     */
    private static Logger logger = LogManager.getLogger();


}
