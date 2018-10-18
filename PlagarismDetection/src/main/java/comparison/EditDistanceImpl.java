package comparison;

import ast.AST;
import ast.ASTCreatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Uses the Levenshtein Distance algorithm to obtain the edit distance between two texts.
 */
public class EditDistanceImpl implements ComparisonTechniques {

    private static Logger logger = LogManager.getLogger();


    /**
     * Calculates the edit distance of characters between two strings.
     *
     * @return the percentage similarity on the basis of distance.
     */
    private double calculateSimilarityPercentage(String astString1, String astString2) {
        int[][] dist = new int[astString1.length() + 1][astString2.length() + 1];

        for (int i = 0; i <= astString1.length(); i++) {
            for (int j = 0; j <= astString2.length(); j++) {

                if (i == 0) {
                    dist[i][j] = j;
                } else if (j == 0) {
                    dist[i][j] = i;
                } else if (astString1.charAt(i - 1) == astString2.charAt(j - 1)) {
                    dist[i][j] = dist[i - 1][j - 1];
                } else {
                    dist[i][j] = min(dist[i][j - 1], dist[i - 1][j], dist[i - 1][j - 1]) + 1;
                }

            }
        }

        double averageLength = (astString1.length() + (double) astString2.length())/2;
        int distance = dist[astString1.length()][astString2.length()];

        double value = 0;
        if (distance < averageLength) {
            value = ((averageLength - distance) *100)/averageLength;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(value));
    }


    /**
     * Returns the minimum number in a set of integers.
     *
     * @param num1 first num.
     * @param num2 second num.
     * @param num3 third num.
     * @return the minimum number.
     */
    private int min(int num1, int num2, int num3) {
        if (num1 <= num2 && num1 <= num3) {
            return num1;
        }

        if (num2 <= num1 && num2 <= num3) {
            return num2;
        } else {
            return num3;
        }
    }

    /**
     * Get result of running edit distance algorithm on given student on given homework.
     * @param homework Homework for which plagiarism is to be run.
     * @param student1 directory name of first student.
     * @param student2 directory name of second student.
     * @return the percentage similarity of two codes.
     */
    @Override
    public double getResult(String homework, String student1, String student2) {
        ASTCreatorImpl parserFacade = new ASTCreatorImpl ( );
        try {
            AST ast1 = new AST(parserFacade.parse(new File(homework + student1 + "/mainAfterMerge.py")));
            StringBuilder astToStringStudent1 = ast1.convertToString();
            AST ast2 = new AST(parserFacade.parse(new File(homework + student2 + "/mainAfterMerge.py")));
            StringBuilder astToStringStudent2 = ast2.convertToString();
            String astStringStudent1 = astToStringStudent1.toString()
                    .replaceAll("\\r", "")
                    .replaceAll("\\n", "")
                    .replaceAll(" ", "");
            String astStringStudent2 = astToStringStudent2.toString()
                    .replaceAll("\\r", "")
                    .replaceAll("\\n", "")
                    .replaceAll(" ", "");

            return calculateSimilarityPercentage(astStringStudent1, astStringStudent2);
        } catch (IOException e) {
            logger.error("Error reading file", e);
        }

        return 0;
    }

    /**
     * gets snippets after the result is generated.
     * @return List with code of student one as first value and rest of the values
     * are the lines in student two's code that are similar to student one.
     */
    @Override
    public List<List<String>> getAllSnippets() {
        return new LinkedList<>();
    }
}
