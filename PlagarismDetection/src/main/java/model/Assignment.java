package model;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Represents the Assignment Model
 */
public class Assignment {


  /**
   * Object Id for the Assignment
   */
  private ObjectId id;
  /**
   * Name of the assignment
   */
  private String name;


  private String hexId;


  public String getHexId() {
    return hexId;
  }

  public void setHexId(String hexId) {
    this.hexId = hexId;
  }

  /**
   * ResultId of all the results in the course
   */
  private List<String> resultsId;

  /**
   * Constructor for the Assignment
   *
   * @param name:              Name of the assignment
   * @param resultsId:ResultId of all the results in the course
   */
  public Assignment(String name, List<String> resultsId) {
    this.name = name;
    this.resultsId = resultsId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getResultsId() {
    return resultsId;
  }

  public void setResultsId(List<String> resultsId) {
    this.resultsId = resultsId;
  }


  /**
   * Default Constructor
   */
  public Assignment() {

  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }
}
