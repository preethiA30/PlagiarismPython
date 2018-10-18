package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Assignment;
import model.Course;
import model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/23/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Database access for assignment collection
 */
public class AssignmentDaoImpl implements AssignmentDao {


    /**
     * Connection to database
     */
    private MongoDatabase mongoDatabase;

    /**
     * Constructor to initialize database connection
     *
     * @param mongoDao:database connection
     */
    public AssignmentDaoImpl(MongoDao mongoDao) {
        this.mongoDatabase = mongoDao.getDBConnection();
    }


    /**
     * Adds all homeworks with new ids and returns the list of homework
     * with updated ids in the database
     *
     * @param courseId      :      CourseId for which homework needs to be added
     * @param homeworkNames : List of all the homework Names in the course
     * @return List of Homeworks with updated ids
     */
    @Override
    public List<Assignment> addAllAssignments(String courseId, List<String> homeworkNames) {

        List<Assignment> assignments = new LinkedList<>();
        MongoCollection collection = mongoDatabase.getCollection("assignment");
        List emptyHomeWorks = new LinkedList();
        // iterate through the list create a document insert one and add into list
        for (String homework : homeworkNames) {
            Document newHomework = new Document();
            newHomework.put("name", homework);
            newHomework.put("resultsId", emptyHomeWorks);
            collection.insertOne(newHomework);
            ObjectId objectId = newHomework.getObjectId("_id");
            Assignment assignment = new Assignment(homework, emptyHomeWorks);
            assignment.setId(objectId);
            assignment.setHexId(objectId.toHexString());
            assignments.add(assignment);

            Bson updateOperationDocument = new Document("$set", assignment);
            collection.updateOne(eq("_id", new ObjectId(objectId.toHexString())), updateOperationDocument);
        }
        return assignments;
    }

    /**
     * Given courseId returns the list of assignments for the same
     *
     * @param courseId : courseId in the database
     * @return List of assignment model
     */
    @Override
    public List<Assignment> getAssignment(String courseId) {

        Course course = getCourse(courseId);
        MongoCollection<Assignment> assignementMongoCollection = mongoDatabase.getCollection("assignment", Assignment.class);
        List<Assignment> assignments = new LinkedList<>();
        // add all assignement for the course
        for (ObjectId assignmentId : course.getAssignment()) {
            assignments.add(assignementMongoCollection.find(eq("_id", assignmentId)).first());
        }
        return assignments;
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

        // added all results and got the ids backs with the results
        List<Result> allResults = addAllResults(results);

        // Getting the assignmentCollection to update assignment
        MongoCollection<Assignment> assignmentMongoCollection = mongoDatabase.getCollection("assignment", Assignment.class);


        // gets the assignment
        Assignment assignment1 = assignmentMongoCollection.find(eq("_id", new ObjectId(homeworkId)), Assignment.class).first();


        List<String> allResultsIds = assignment1.getResultsId();
        for (Result result : allResults) {
            allResultsIds.add(result.getHexId());
        }
        assignment1.setResultsId(allResultsIds);
        // add all results to table
        MongoCollection<Document> collectionAssignment = mongoDatabase.getCollection("assignment");
        Bson updateOperationDocument = new Document("$set", assignment1);
        collectionAssignment.updateOne(eq("_id", new ObjectId(homeworkId)), updateOperationDocument);

        return allResults;
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
        // Gets list of Results Id
        MongoCollection<Assignment> assignmentMongoCollection = mongoDatabase.getCollection("assignment", Assignment.class);
        Assignment assignment = assignmentMongoCollection.find(eq("_id", new ObjectId(assignmentId)), Assignment.class).first();

        List<String> resultsId = assignment.getResultsId();
        // Gets all assignments
        MongoCollection<Result> resultMongoCollection = mongoDatabase.getCollection("result", Result.class);
        List<Result> results = new ArrayList<>();
        for (String resultId : resultsId) {
           // Result result = resultMongoCollection.find(eq("_id", new ObjectId(resultId)), Result.class).first();
            results.add(resultMongoCollection.find(eq("_id", new ObjectId(resultId)), Result.class).first());
        }

        return results;
    }



    /// main method
    public boolean deleteResultsForStrategyType(String assignmentId, String strategy) {
        try {
            // all results for a
            List<Result> resultList = getResults(assignmentId);
            MongoCollection<Assignment> assignmentMongoCollection = mongoDatabase.getCollection("assignment", Assignment.class);
            Assignment assignment1 = assignmentMongoCollection.find(eq("_id", new ObjectId(assignmentId)), Assignment.class).first();
            for (Result result : resultList) {
                Result result1 = getResultByResultId(result.getHexId());
                if (result1.getType().toString().equals(strategy)) {
                  //  deleteResultByResultId(result.getHexId(), assignmentId);
                    assignment1.getResultsId().remove(result.getHexId());
                }
            }

            // update operation
            Bson updateOperationDocument = new Document("$set", assignment1);
            assignmentMongoCollection.updateOne(eq("_id", new ObjectId(assignmentId)), updateOperationDocument);
            return true;
        }catch (Exception e){
            logger.error("Error in deleting results"+e);
            return  false;
        }

    }

    /**
     * Gets Result by resultId
     * @param resultId: resultId of the result database
     * @return Result model signifying result
     */
    @Override
    public Result getResultByResultId(String resultId) {
        MongoCollection<Result> collection = mongoDatabase.getCollection("result", Result.class);
        return collection.find(eq("_id", new ObjectId(resultId))).first();

    }





    /**
     * Inserts all results into the results collections and returns all the results Id
     * in the backend
     *
     * @param result: List of results
     * @return List of ids of the results inserted in the database
     */
    private List<Result> addAllResults(List<Result> result) {

        MongoCollection<Document> collection = mongoDatabase.getCollection("result");
        List<Result> allResults = new LinkedList<>();
        for (Result res : result) {
            Document newResult = new Document();
            newResult.put("student1", res.getStudent1());
            newResult.put("student2", res.getStudent2());
            newResult.put("percentage", res.getPercentage());
            newResult.put("snippets", res.getSnippets());
            newResult.put("type", res.getType().toString());
            collection.insertOne(newResult);


            ObjectId resultId = newResult.getObjectId("_id");
            res.setObjectId(resultId);
            newResult.put("hexId", resultId.toHexString());
            res.setHexId(resultId.toHexString());
            Bson updateOperationDocument = new Document("$set", newResult);
            collection.updateOne(eq("_id", new ObjectId(resultId.toHexString())), updateOperationDocument);

            allResults.add(res);
        }
        return allResults;
    }

    /**
     * Given a course id, gets the course
     *
     * @param courseId: Courseid in the databse
     * @return: The Course instance
     */
    private Course getCourse(String courseId) {
        MongoCollection<Course> courseMongoCollection = mongoDatabase.getCollection("course", Course.class);
        return courseMongoCollection.find(eq("_id", new ObjectId(courseId))).first();
    }


    /**
     * Logger for class AssignmnetDaoImpl
     */
    private static Logger logger = LogManager.getLogger();

}
