package model;


import com.fasterxml.jackson.annotation.JsonInclude;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Represents a User object.
 */
public class User {
    private ObjectId id;
    private String username;
    private String password;
    private String gitUsername;
    private String gitPassword;
    private List<ObjectId> courses;
    private String hexId;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Boolean isAdmin;

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }


    /**
     * Construct a User object with the following fields.
     *
     * @param username       String representing username.
     * @param password       String representing password.
     * @param gitUsername String representing GitHub username.

     * @param gitHubPassword String representing GitHub password.
     */
    public User(String username, String password,
                String gitUsername, String gitHubPassword) {
        this.username = username;
        this.password = password;
        this.gitUsername = gitUsername;
        this.gitPassword = gitHubPassword;
    }


    public User(ObjectId userId, String username, String password,
                String gitUsername, String gitHubPassword) {
        this.username = username;
        this.password = password;
        this.gitUsername = gitUsername;
        this.gitPassword = gitHubPassword;
        this.id = userId;
    }

    public User(ObjectId userId, String username, String password,
                String gitUsername, String gitHubPassword,Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.gitUsername = gitUsername;
        this.gitPassword = gitHubPassword;
        this.id = userId;
        this.isAdmin=isAdmin;
    }


    public User() {

    }

    /**
     * gets ObjectIds of all the courses
     *
     * @return
     */
    public List<ObjectId> getCourses() {
        return courses;
    }

    /**
     * Sets the courses ids
     *
     * @param courses
     */
    public void setCourses(List<ObjectId> courses) {
        this.courses = courses;

    }


    /**
     * Getter for username.
     *
     * @return String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     *
     * @param username New username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for password.
     *
     * @return String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param password New password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for GitHub username.
     *
     * @return String.
     */
    public String getGitUsername() {
        return gitUsername;
    }

    /**
     * Setter for GitHUb username.
     *
     * @param username New GitHub username.
     */
    public void setGitUsername(String username) {
        this.gitUsername = username;
    }

    /**
     * Getter for GitHub password.
     *
     * @return String.
     */
    public String getGitPassword() {
        return gitPassword;
    }

    /**
     * Setter for GitHub password.
     *
     * @param password New GitHub password.
     */
    public void setGitPassword(String password) {
        this.gitPassword = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getHexId() {
        return hexId;
    }

    public void setHexId(String hexId) {
        this.hexId = hexId;
    }


}
