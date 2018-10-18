package service.plagiarism;

import comparison.*;
import model.Result;
import service.counter.CounterServiceImpl;
import java.io.File;
import java.util.*;

/**
 * Get plagiarism results for all students for particular homework.
 */
public class PlagiarismResultImp implements PlagiarismResult {

    // Weights were set to mimic Stanford MOSS results.
    static final double WEIGHT_LCS = 0.27;
    static final double WEIGHT_FINGERPRINTING = 0.5;
    static final double WEIGHT_EDIT_DIST = 0.23;
    private CounterServiceImpl counterServiceImpl;

    /**
     * Generate plagiarism results for homework
     *
     * @param homeworkPath    path to homework directory
     * @param strategy        strategy to be used
     * @param numberOfResults number of results required.
     * @return List of results.
     */
    public List<Result> generateResults(String homeworkPath, Strategy strategy, int numberOfResults, CounterServiceImpl counterService) {
        MergeFiles mf = new MergeFiles(homeworkPath);
        mf.mergeFiles();
        List<Result> results = new ArrayList<>();
        List<String> studentNames = this.getStudentDirectoryName(homeworkPath);
        if(strategy==null)
            strategy=Strategy.ALL;
        compareResults(studentNames, homeworkPath, results, strategy);

        int n = numberOfResults;
        if (results.size() < numberOfResults) {
            n = results.size();
        }

        counterServiceImpl = counterService;
        counterServiceImpl.updateStats(n);
        return results.subList(0, n);
    }

    /**
     * Compare results
     *
     * @param studentNames Name of students.
     * @param homeworkPath homework directory path
     * @param results      the list of results.
     * @param strategy     the strategy to be used.
     */
    private void compareResults(List<String> studentNames, String homeworkPath, List<Result> results, Strategy strategy) {
        // Comparison Strategies if no strategy is given
        ComparisonTechniques ct1 = getTechnique(Strategy.LCS);
        ComparisonTechniques ct2 = getTechnique(Strategy.FINGERPRINT);
        ComparisonTechniques ct3 = getTechnique(Strategy.EDIT_DISTANCE);
        ComparisonTechniques ct = null;

        // Comparison Strategy if strategy is given.
        if (!strategy.equals(Strategy.ALL)) {
            ct = getTechnique(strategy);
        }

        for (int i = 0; i < studentNames.size(); i++) {
            for (int j = i + 1; j < studentNames.size(); j++) {
                String student1 = studentNames.get(i);
                String student2 = studentNames.get(j);

                Result result = new Result();
                double value;
                List<List<String>> snippets;
                if (!strategy.equals(Strategy.ALL) && ct != null) {
                    value = ct.getResult(homeworkPath, student1, student2);
                    if (strategy.toString().equals("LCS")) {
                        snippets = ct.getAllSnippets();
                    } else {
                        ct1.getResult(homeworkPath, student1, student2);
                        snippets = ct1.getAllSnippets();
                    }
                } else {
                    double value1 = ct1.getResult(homeworkPath, student1, student2);
                    double value2 = ct2.getResult(homeworkPath, student1, student2);
                    double value3 = ct3.getResult(homeworkPath, student1, student2);
                    value = WEIGHT_LCS * value1 + WEIGHT_FINGERPRINTING * value2 + WEIGHT_EDIT_DIST * value3;
                    snippets = ct1.getAllSnippets();
                }

                result.setStudent1(student1);
                result.setStudent2(student2);
                result.setPercentage(value);
                result.setSnippets(snippets);
                result.setType(strategy);
                results.add(result);

            }
        }
        results.sort(Comparator.comparing(Result::getPercentage));
        Collections.reverse(results);
    }

    /**
     * Gets the comparison technique from specified strategy.
     *
     * @param strategy
     * @return ComparisonTechniques
     */
    private ComparisonTechniques getTechnique(Strategy strategy) {
        if (strategy.toString().equals("LCS")) {
            return new LCSImp();
        } else if (strategy.toString().equals("FINGERPRINT")) {
            return new DocumentFingerprinting();
        } else {
            return new EditDistanceImpl();
        }
    }

    /**
     * gets all the student directory name for that homework.
     *
     * @param homeworkPath
     * @return List of student directory names.
     */
    private List<String> getStudentDirectoryName(String homeworkPath) {
        List<String> studentDirectoryName = new ArrayList<>();
        File directory = new File(homeworkPath);
        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isDirectory()) {
                studentDirectoryName.add(file.getName());
            }
        }

        return studentDirectoryName;
    }

}
