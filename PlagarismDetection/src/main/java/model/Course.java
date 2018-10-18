package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */
public class Course {

    /**
     * ObjectId of
     */
    @JsonIgnore
    private ObjectId id;


    private String hexid;

    public String getHexid() {
        return hexid;
    }

    public void setHexid(String hexid) {
        this.hexid = hexid;
    }

    /**
     * Name of the Course
     */
    private String name;

    /**
     * The repo url for the course
     */
    private String repoUrl;

    /**
     * The assignment of the url
     */
    private List<ObjectId> assignments;

    /**
     * Course instance for  the course
     *
     * @param name:    Name of the Course
     * @param repoUrl: The repo url for the course
     */
    public Course(String name, String repoUrl) {
        this.name = name;
        this.repoUrl = repoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public List<ObjectId> getAssignment() {
        return assignments;
    }

    public void setAssignment(List<ObjectId> assignments) {
        this.assignments = assignments;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Default Constructor
     */
    public Course() {

    }
}
