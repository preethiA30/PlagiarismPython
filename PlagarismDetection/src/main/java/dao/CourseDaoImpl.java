package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Assignment;
import model.Course;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/22/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Database services relating to all the courses
 */
public class CourseDaoImpl implements CourseDao {


    /**
     * Logger for class CourseDaoImpl
     */
    private static Logger logger = LogManager.getLogger();

    /**
     * Mongo Database connection
     */
    MongoDatabase mongoDatabase;


    /**
     * Initializes the database connection
     *
     * @param mongoDao: Mongo database connection
     */
    public CourseDaoImpl(MongoDao mongoDao) {
        this.mongoDatabase = mongoDao.getDBConnection();
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

        // Get all coursesId with userId
        List<ObjectId> courseIds = getCourseIdsForUser(userId);

        // Get all courseInfo from CourseIds
        return courseInfoForAllCourses(courseIds);

    }

    /**
     * Adds a course for the given User
     *
     * @param course       : Given Course of the loggedIn User
     * @param userId       : userId who is loggedIn
     * @param assignments: Assignments for the given course for the given course
     * @returns: the new Course information with the courseId updated
     */
    @Override
    public Course addCourse(Course course, String userId, List<Assignment> assignments) {
        // UserId to be updated
        User user = getUser(userId);

        //Insert new course
        ObjectId objectIdForNewCourse = insertNewCourse(course, assignments);
        course.setId(objectIdForNewCourse);

        // Update User
        user.getCourses().add(objectIdForNewCourse);
        updateUser(user, userId);

        return course;
    }

    /**
     * Given course Id returns the course with the id in the database
     *
     * @param courseId : CourseId in the databse
     * @return: Course with courseid if sucessful else return a null
     */
    @Override
    public Course getCourseByCourseId(String courseId) {
        MongoCollection<Document> courseCollection;
        Course course = null;
        try {
            courseCollection = mongoDatabase.getCollection("course");
            return courseCollection.find(eq("_id", new ObjectId(courseId)), Course.class).first();
        } catch (Exception exp) {
            logger.error("Error in geting course" + exp);
        }
        return course;
    }


    /**
     * Given a course add a new course in collections and
     * return the object id of the new course generated
     *
     * @param course: new Course document to be created
     * @return : Objectid of the new course else returns null
     */
    private ObjectId insertNewCourse(Course course, List<Assignment> assignments) {
        Document newCourse = null;
        MongoCollection<Document> courseCollection = null;
        try {
            courseCollection = mongoDatabase.getCollection("course");
            newCourse = new Document();
            newCourse.put("name", course.getName());
            newCourse.put("repoUrl", course.getRepoUrl());
        } catch (Exception ex) {
            logger.error("Error in insertnf Course");
        }
        if (newCourse != null)
            newCourse.put("assignment", getAssignementObjectIds(assignments));
        else
            return null;
        courseCollection.insertOne(newCourse);
        return newCourse.getObjectId("_id");
    }

    /**
     * Updates the new the user with userId to the given User details
     *
     * @param user:   Given user to user
     * @param userId: UserId to be update
     */
    private void updateUser(User user, String userId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Bson updateOperationDocument = new Document("$set", user);
        collection.updateOne(eq("_id", new ObjectId(userId)), updateOperationDocument);
    }

    /**
     * Helper Functions
     */
    /**
     * Given CourseIds gets all info for the courses
     *
     * @param courseIds: List of CourseId
     * @return Course Info for all the courses
     */
    private List<Course> courseInfoForAllCourses(List<ObjectId> courseIds) {
        // a new list of courses
        List<Course> allCourses = new LinkedList<>();

        MongoCollection<Course> collectionCourse = mongoDatabase.getCollection("course", Course.class);

        for (ObjectId objectId : courseIds) {
            Course course = collectionCourse.find(eq("_id", objectId)).first();
            course.setHexid(objectId.toHexString());
            allCourses.add(course);
        }
        return allCourses;
    }

    /**
     * @param userId : userId in the database
     * @return List of CourseIds that a specific user is teaching
     */
    private List<ObjectId> getCourseIdsForUser(String userId) {
        // Gets collection user
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        Document user = collection.find(eq("_id", new ObjectId(userId))).first();
        if (user == null)
            return new LinkedList<>();
        return (List) user.get("courses");

    }


    /**
     * Given userid returns the docment with id as userId
     *
     * @param userId: UseriD in the database
     * @return the User
     */
    private User getUser(String userId) {
        MongoCollection<User> collection = mongoDatabase.getCollection("user", User.class);
        return collection.find(eq("_id", new ObjectId(userId))).first();
    }


    /**
     * Given a list of assignments returns the list of object ids
     *
     * @param assignments: assignments with all info
     * @return the list of object ids of the assignment
     */
    private List<ObjectId> getAssignementObjectIds(List<Assignment> assignments) {
        List<ObjectId> objIds = new LinkedList();
        for (Assignment assignment : assignments) {
            objIds.add(assignment.getId());
        }
        return objIds;
    }


}
