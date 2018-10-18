package model;


/**
 * Represents user credentials for login.
 */
public class Credentials {

  public Credentials() {

  }

  /**
   * UserId
   */
  private String username;
  /**
   * Password for the credentials
   */
  private String password;

  /**
   * Creates a credentials instance
   *
   * @param username: UsrId
   * @param password: Password for the credentials
   */
  public Credentials(String username, String password) {
    this.username = username;
    this.password = password;
  }


  /**
   * Get userId.
   *
   * @return this userId.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets userId.
   *
   * @param username the new userId.
   */
  public void setUsername(String username) {
    this.username = username;
  }


  /**
   * Get password.
   *
   * @return this password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password.
   *
   * @param password the new password.
   */
  public void setPassword(String password) {
    this.password = password;
  }


}
