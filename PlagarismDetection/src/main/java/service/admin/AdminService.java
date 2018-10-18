package service.admin;

import model.User;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 4/5/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Represents functional specification of all admin services to be implemented
 */
public interface AdminService {

    /**
     * Gets all the Users in the Database
     * @return List of Users with all information populated in the User model
     */
    List<User> getAllUsers();

    /**
     * Given a userId of the user, deletes the user if found in the system
     * @param userId: User Id of the user to be deleted
     * @return true if successfully deleted returns false
     */
    boolean deleteUser(String userId);
}
