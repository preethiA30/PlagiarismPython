package service.register;

import dao.InstructorDaoImpl;
import model.User;
/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Represents service for registering a new user.\
 */
public class RegisterServiceImpl implements RegisterService {

    /**
     * Instructor Dao for database access
     */
    InstructorDaoImpl instructorDao;

    /**
     * Initializing the database access
     * @param instructorDao: Instructor Collection dao service to initialize the dao
     */
    RegisterServiceImpl(InstructorDaoImpl instructorDao) {
        this.instructorDao = instructorDao;
    }

    /**
     * @param username    : Username for login system
     * @param password    : Password for the accessing the system
     * @param gitUsername : Password for the accessing the system
     * @param gitPassword : Password for accessing the system
     * @return User if the creating the new user was successful else return null
     */
    @Override
    public User registerNewUser(String username, String password, String gitUsername, String gitPassword)throws DuplicateUsernameException {

            if(!isDuplicateUsername(username))
            return instructorDao.createUser(username, password, gitUsername, gitPassword);
            else{
                throw new DuplicateUsernameException("DuplicatesExists");
            }
    }

    public boolean isDuplicateUsername(String username) {
        return instructorDao.isDuplicateUsername(username);
    }


}
