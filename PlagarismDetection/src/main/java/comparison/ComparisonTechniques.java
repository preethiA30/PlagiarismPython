package comparison;

import java.util.List;

/**
 * Interface to be for all comparison techniques.
 */
public interface ComparisonTechniques {

    /**
     * gets comparison result between two students.
     * @param homework Homework for which plagiarism is to be run.
     * @param student1 directory name of first student.
     * @param student2 directory name of second student.
     * @return the similarity percentage of both codes.
     */
    double getResult(String homework, String student1, String student2);

    /**
     * gets snippets after the result is generated.
     * @return List with code of student one as first value and rest of the values
     * are the lines in student two's code that are similar to student one.
     */
    List<List<String>> getAllSnippets();
}
