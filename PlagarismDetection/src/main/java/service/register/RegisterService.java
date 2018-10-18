package service.register;

import model.User;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */
public interface RegisterService {

    /**
     * @param username:    Username for login system
     * @param password:    Password for the accessing the system
     * @param gitUsername: Password for the accessing the system
     * @param gitPassword: Password for accessing the system
     * @return User if successfully registered else returns null
     */

    User registerNewUser(String username, String password, String gitUsername, String gitPassword) throws DuplicateUsernameException;

}

