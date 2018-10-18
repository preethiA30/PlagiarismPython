package service.login;

import dao.InstructorDao;
import dao.InstructorDaoImpl;
import org.bson.Document;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * This class represents service Impl for Login
 */

public class LoginServiceImpl implements LoginService {

    /**
     * InstructorDao represents all dao access for Instructor
     */
    InstructorDao instructorDao;

    /**
     * Constructor for instructor sets the Dao for Instructor
     *
     * @param instructorDao: InstructorDao represents all dao access for Instructor
     */
    public LoginServiceImpl(InstructorDaoImpl instructorDao) {
        this.instructorDao = instructorDao;
    }

    /**
     * Verifies if the credentials are correct for the system
     *
     * @param username : Username of the User
     * @param password : Password of the user
     * @return LoginResult with Valid or invalid credentials
     */
    @Override
    public Document login(String username, String password) {
        return instructorDao.verifyCredentials(username, password);
    }
}
