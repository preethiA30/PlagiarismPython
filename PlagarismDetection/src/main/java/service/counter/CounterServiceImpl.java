package service.counter;

import dao.CounterDaoImpl;
import model.Counter;

import java.util.List;

/**
 * @author Preethi Anbunathan
 * @project PlagarismDetection
 * @date 4/2/18
 * @email anbunathan.p@husky.neu.edu
 */

/**
 * All services related to counter corresponding to reports generated
 */
public class CounterServiceImpl implements CounterService {

  static int resultCounter = 0;
  private CounterDaoImpl counterDao;

  /**
   * Initializing the database access
   *
   * @param counterDao: Counter Collection dao service to initialize the dao
   */
  public CounterServiceImpl(CounterDaoImpl counterDao) {
    this.counterDao = counterDao;
  }

  /**
   * Increments the counter everytime generate result method is called and returns
   * the number of reports generated
   *
   * @param numberOfResults: Number of Plagiarism results generated
   * @return total results generated
   */
  @Override
  public int updateStats(int numberOfResults) {

    resultCounter++;
    counterDao.counterUpdate(resultCounter, numberOfResults);
    return resultCounter;
  }

  /**
   * Increments the counter everytime generate result method is called and returns
   * the number of reports generated
   *
   * @return total results generated for days set by the system
   */
  @Override
  public List<Counter> getStatistics() {
    return counterDao.getStatistics();
  }
}
