package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import service.plagiarism.Strategy;

import java.util.List;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/21/18
 * @email thakker.m@husky.neu.edu
 */
public class Result {

  /**
   * Default Constructor
   */
  public Result() {

  }

  public ObjectId getObjectId() {
    return objectId;
  }

  public void setObjectId(ObjectId objectId) {
    this.objectId = objectId;
  }

  private ObjectId objectId;

  private String hexId;


  public String getHexId() {
    return hexId;
  }

  public void setHexId(String hexId) {
    this.hexId = hexId;
  }

  public static Logger getLogger() {
    return logger;
  }

  public static void setLogger(Logger logger) {
    Result.logger = logger;
  }

  /**
   * Name of the student1
   */
  private String student1;

  public String getStudent1() {
    return student1;
  }

  public void setStudent1(String student1) {
    this.student1 = student1;
  }

  public String getStudent2() {
    return student2;
  }

  public void setStudent2(String student2) {
    this.student2 = student2;
  }

  /**
   * Name of the student2
   */
  private String student2;


  /**
   * A relative number assigned by the algorithm
   * specifying how plagarised are the files
   */
  private double percentage;

  /**
   * Similar code snippets of two students.
   */
  private List<List<String>> snippets;

  /**
   * The type of the algorithm used to compute the result
   */
  private Strategy type;

  /**
   * @param percentage:   A relative number assigned by the algorithm
   *                      specifying how plagarised are the files
   * @param type:         The type of the algorithm used to compute the result
   */
  public Result(double percentage, Strategy type) {
    this.percentage = percentage;
    this.type = type;
  }


  public double getPercentage() {
    return percentage;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }

  public Strategy getType() {
    return type;
  }

  public void setType(Strategy type) {
    this.type = type;
  }

  /**
   * Add a new course
   */
  private static Logger logger = LogManager.getLogger();

  /**
   * Checks if result are equal
   * @param obj result object to equate
   * @return true if they are equal otherwise false.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != this.getClass())
      return false;

    Result result = (Result) obj;

    if (Math.abs(this.getPercentage() - result.getPercentage()) > 5.00)
      return false;

    else if ((this.getStudent1().equals(result.getStudent1()) && this.getStudent2().equals(result.getStudent2())) ||
    (this.getStudent1().equals(result.getStudent2()) && this.getStudent2().equals(result.getStudent1())))
      return true;

    return false;
  }

  /**
   * Hashcode of result.
   * @return hashcode.
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Get similar code snippets of two students
   * @return snippet
   */
  public List<List<String>> getSnippets() {
    return snippets;
  }

  /**
   * Sets similar snippets of two student.
   * @param snippets
   */
  public void setSnippets(List<List<String>> snippets) {
    this.snippets = snippets;
  }
}
