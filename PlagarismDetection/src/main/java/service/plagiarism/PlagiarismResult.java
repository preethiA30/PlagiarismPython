package service.plagiarism;

import model.Result;
import service.counter.CounterServiceImpl;

import java.util.List;

/**
 * Get plagiarism results for all students.
 */
public interface PlagiarismResult {
    /**
     * Generate plagiarism results for homework
     *
     * @param homeworkPath path to homework directory
     * @param strategy strategy to be used
     * @param numberOfResults number of results required.
     * @return List of results.
     */
    List<Result> generateResults(String homeworkPath, Strategy strategy, int numberOfResults, CounterServiceImpl counterService);
}
