package dao;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Dao for the instructor collection
 */
public class InstructorDaoImpl implements InstructorDao {

    /**
     * Logger for Instructor Dao
     */
    private static Logger logger = LogManager.getLogger();

    /**
     * MongoDatabase Connection
     */
    MongoDatabase mongoDatabase;

    /**
     * Instructor Dao Impl constructor to initialize the
     * mongodatabase connection
     *
     * @param mongoDao: Mongo Database Connection
     */
    public InstructorDaoImpl(MongoDao mongoDao) {
        this.mongoDatabase = mongoDao.getDBConnection();
    }


    /**
     * Creates a new user in User Collection with given information
     *
     * @param username    :    Username of the new user
     * @param password    :    Password of the new user
     * @param gitUsername : Git Username of the new user
     * @param gitPassword : Git Username of the new User
     */
    @Override
    public User createUser(String username, String password, String gitUsername, String gitPassword) {
        MongoCollection collection = mongoDatabase.getCollection("user");
        List<ObjectId> courses = new ArrayList<>();

        Document document = new Document("username", username)
                .append("password", password)
                .append("gitUsername", gitUsername)
                .append("gitPassword", gitPassword)
                .append("courses", courses)
                .append("isAdmin", false);

        collection.insertOne(document);

        ObjectId id = (ObjectId) document.get("_id");

        User newUser = new User(id, username, password, gitUsername, gitPassword);
        newUser.setCourses(courses);

        return newUser;
    }

    /**
     * Verifies the if login credentials are valid or not
     *
     * @param username : Username of the user
     * @param password : The password of the user
     * @return a document of type user if found else returns null
     */
    @Override
    public Document verifyCredentials(String username, String password) {

        Document user = null;
        try {
            user = findUserByCredentials(username, password);
        } catch (Exception exp) {
            logger.error("Exception in Login " + exp);
        }
        return user;
    }

    /**
     * Find a user in the database given username and password.
     *
     * @param username the given username to match.
     * @param password the given password to match.
     * @return a User which is a document in the mongo database
     */
    public Document findUserByCredentials(String username, String password) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("user");
        return collection.find(and(eq("username", username), eq("password", password))).first();

    }


    /**
     * Given userid returns the docment with id as userId
     *
     * @param userId: UserId in the database
     * @return the User Document
     */
    public User getUser(String userId) {
        MongoCollection<User> collection = mongoDatabase.getCollection("user", User.class);
        return collection.find(eq("_id", new ObjectId(userId))).first();
    }

    /**
     * Gets a list Of Users for the admin to display
     *
     * @return List of users
     */
    @Override
    public List<User> getAllUsers() {
        MongoCollection<User> collection = mongoDatabase.getCollection("user", User.class);
        List<User> users = new LinkedList<>();

        Iterable<User> iterable = collection.find();
        for (User user : iterable) {
            user.setHexId(user.getId().toString());
            users.add(user);
        }
        return users;
    }

    /**
     * Given UserId to be deleted
     *
     * @param userId: UserId of the user to be deleted
     * @return true if the user is deleted successfully, else false otherwise
     */
    @Override
    public boolean deleteUserByUserId(String userId) {
        MongoCollection<User> collection = mongoDatabase.getCollection("user", User.class);
        DeleteResult deleteResult = collection.deleteOne(eq("_id", new ObjectId(userId)));
        if (deleteResult.getDeletedCount() >= 1)
            return true;
        else
            return false;
    }


    /**
     * Checks inn the system if a similar username exisits in the system database
     *
     * @param username: username to check for duplicate
     * @return true if duplicate found else false
     */
    public boolean isDuplicateUsername(String username) {
        MongoCollection<User> userMongoCollection = mongoDatabase.getCollection("user", User.class);
        FindIterable findIterable = userMongoCollection.find(eq("username", username));
        if (findIterable.first() != null)
            return true;
        else
            return false;
    }

}

