package service.login;

import org.bson.Document;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Represents Login Service
 */
public interface LoginService {

    /**
     * Verifies if the crendentials are correct for the system
     *
     * @param username: Username of the User
     * @param password: Password of the user
     * @return LoginResult with Valid or invalid credentials
     */
    Document login(String username, String password);
}
