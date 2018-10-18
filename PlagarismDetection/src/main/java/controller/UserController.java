package controller;

import model.Credentials;
import model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.admin.AdminServiceImpl;
import service.login.LoginService;
import service.register.DuplicateUsernameException;
import service.register.RegisterService;

import java.util.List;


/**
 * Represents the User Controller
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {


  /**
   * Register Service for the user
   */
  private RegisterService registerService;
  /**
   * Login Service for the user
   */
  private LoginService loginService;


  private AdminServiceImpl adminService;

  /**
   * User Controller Constructor
   *
   * @param registerService:Register Service for the user
   * @param loginService:Login       Service for the user
   */
  public UserController(RegisterService registerService, LoginService loginService, AdminServiceImpl adminService) {
    this.loginService = loginService;
    this.registerService = registerService;
    this.adminService = adminService;
  }

  /**
   * Endpoint to login for a user
   *
   * @param credentials: Gets user crendentials
   * @return User details if the id password are correct else
   * returns a Not_Found
   */
  @PostMapping("/api/login")
  public ResponseEntity<String> getlogin(@RequestBody Credentials credentials) {
    Document user = loginService.login(credentials.getUsername(), credentials.getPassword());
    if (user != null)
      return new ResponseEntity(user.toJson(), HttpStatus.ACCEPTED);
    else
      return new ResponseEntity(null, HttpStatus.NOT_FOUND);
  }

  /**
   * Creates a new user if and only if :
   * Username is unique accross all the users
   *
   * @param user: User details (username,password, gitUsername, gitPassword)
   * @return User details if successfully inserted in DB else returns partial credentials
   */
  @PostMapping("/api/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) {
    User user1 = null;
    try {
      user1 = registerService.registerNewUser(user.getUsername(), user.getPassword(), user.getGitUsername(), user.getGitPassword());
      return new ResponseEntity(user1, HttpStatus.PARTIAL_CONTENT);
    } catch (DuplicateUsernameException exp) {
      logger.error("Duplicate Username user"+exp);
      return new ResponseEntity("Username Already In Use!", HttpStatus.BAD_REQUEST);
    }catch (Exception exp){
      logger.error("Some error in Registering a new user"+exp);
      return new ResponseEntity(user1, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }


  /**
   * Gets all the Users in the System
   * @return List Of all Users in the system
   */
  @RequestMapping(value = "/api/admin/user", method = RequestMethod.GET)
  public ResponseEntity<List<User>> getUsers() {
    try {
      List<User> users = adminService.getAllUsers();
      return new ResponseEntity(users, HttpStatus.ACCEPTED);
    } catch (Exception exp) {
      logger.error("Error in getting all the users"+exp);
      return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }


  /**
   *
   * Deletes the user and returns the list of users after deleting the user
   * @param userId: Id of the User in the system
   * @return List of Users after deleting the users
   */
  @RequestMapping(value = "/api/admin/user/delete/{userId}", method = RequestMethod.DELETE)
  public ResponseEntity<List<User>> deleteUser(@PathVariable String userId) {
    try {
      boolean deleted = adminService.deleteUser(userId);
      if (deleted)
        return new ResponseEntity("Deleted", HttpStatus.ACCEPTED);
      else
        return new ResponseEntity("Not Deleted", HttpStatus.NOT_FOUND);
    } catch (Exception exp) {
      return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }


  /**
   * Logger
   */
  private static Logger logger = LogManager.getLogger();


}
