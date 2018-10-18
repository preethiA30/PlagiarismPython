package dao;

import model.Counter;

import java.util.List;

/**
 * @author Preethi Anbunathan
 * @project PlagarismDetection
 * @date 4/2/18
 * @email anbunathan.p@husky.neu.edu
 */
public interface CounterDao {

    /**
     * Given the number of results generated is debited in the Database
     *
     * @param numberOfResults: Number of plagiarism results generated
     * @param counter: Number of times the Plagiarism tests have been done
     */
    void counterUpdate(int counter, int numberOfResults);

    List<Counter> getStatistics();
}
