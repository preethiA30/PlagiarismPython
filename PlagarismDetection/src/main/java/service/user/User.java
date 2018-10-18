package service.user;

public interface User {

  /**
   * Checks user credentials for login functionality.
   * @param username User's username.
   * @param password User's password.
   * @return true if credentials match database, else false.
   */
  boolean login(String username, String password);

  /**
   * User service that creates a new instance of User in database.
   * @param firstName First name of the new user.
   * @param lastName Last name of the new user.
   * @param username Username of the new user.
   * @param password Password of the new user.
   * @param gitHubUsername GitHub username of the new user.
   * @param gitHubPass GitHub password of the new user.
   */
  void register(String firstName, String lastName, String username, String password,
                String gitHubUsername, String gitHubPass);

}
