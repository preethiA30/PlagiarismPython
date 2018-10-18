package service.admin;

import dao.InstructorDao;
import model.User;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 4/5/18
 * @email thakker.m@husky.neu.edu
 */
public class AdminServiceImpl implements AdminService {

    /**
     * Instructor Dao for the admin service
     */
    InstructorDao instructorDao;

    /**
     * Admin Service Impl constructor to intialize requrred Daos
     *
     * @param instructorDao: All data access for instructor dao
     */
    AdminServiceImpl(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    /**
     * Reports all the Users in the system.
     *
     * @return All the Users in the System
     */
    public List<User> getAllUsers() {
        return instructorDao.getAllUsers();
    }

    /**
     * Deletes a specific user with userId
     *
     * @param userId: User Id of the user to be deleted
     * @return true if the user is deleted else false otherwise
     */
    @Override
    public boolean deleteUser(String userId) {
        return instructorDao.deleteUserByUserId(userId);
    }
}
