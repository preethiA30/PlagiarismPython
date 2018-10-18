package service.counter;

import model.Counter;

import java.util.List;

/**
 * @author Preethi Anbunathan
 * @project PlagarismDetection
 * @date 4/2/18
 * @email anbunathan.p@husky.neu.edu
 */

/**
 * Represents the Statistics update feature
 */
public interface CounterService {

    /**
     *
     * @param numberOfResults: Number of Plagiarism results generated
     * @return Count of number of times the Plagiarism tests have been performed
     */

    int updateStats(int numberOfResults);

    /**
     * Increments the counter everytime generate result method is called and returns
     * the number of reports generated
     * @return total results generated
     */
    List<Counter> getStatistics();

}



