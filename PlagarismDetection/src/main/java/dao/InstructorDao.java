package dao;

import model.User;
import org.bson.Document;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */


public interface InstructorDao {

    /**
     * Creates a new user in User Collection with given information
     *
     * @param username:    Username of the new user
     * @param password:    Password of the new user
     * @param gitUsername: Git Username of the new user
     * @param gitPassword: Git Username of the new User
     * @return User with the objectId in the database as Id and other information as given
     */
    User createUser(String username, String password, String gitUsername, String gitPassword);

    /**
     * Verifiies the if login credentials are valid or not
     *
     * @param username: Username of the user
     * @param password: The password of the user
     * @return User with the objectId in the database as Id and other information as given
     */
    Document verifyCredentials(String username, String password);


    /**
     * Given userid returns the docment with id as userId
     *
     * @param userId: UseriD in the database
     * @return the User Document
     */
    User getUser(String userId) ;


    /**
     * Get All Users in the system at current time
     * @return All the Users in the system at current time.
     */
    List<User> getAllUsers();


    /**
     * Given a userid, deletes the user from the database
     * @param userId: UserId of the user to be deleted
     * @return true if the user is deleted else false otherwise
     */
    boolean deleteUserByUserId(String userId);

}
