package comparison;

import ast.ASTCreatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Sakshi Tonwer
 * @project PlagiarismDetection
 *
 * Implements LCS on NodeMap
 */
public class LCSImp implements ComparisonTechniques {
    private static Logger logger = LogManager.getLogger();
    private double result = 0;
    private String homeworkPath;
    private String student1;
    private String student2;
    private List<List<String>> snippets = new ArrayList<>();
    private final String MAIN_AFTER_MERGE = "mainAfterMerge.py";

    /**
     * Generate results.
     * @param homework Homework for which plagiarism is to be run.
     * @param student1 directory name of first student.
     * @param student2 directory name of second student.
     * @return percentage similarity.
     */
    @Override
    public double getResult(String homework, String student1, String student2) {
        ASTCreatorImpl astCreator = new ASTCreatorImpl();
        this.homeworkPath = homework;
        this.student1 = student1;
        this.student2 = student2;
        try {
            List<List<Integer>> astList1 = astCreator.parseWithLine(
                    new File(homework + student1 + "/"  + MAIN_AFTER_MERGE));
            List<List<Integer>> astList2 = astCreator.parseWithLine(
                    new File(homework + student2 + "/" + MAIN_AFTER_MERGE));

            calculateLCS(astList1, astList2);

        } catch (IOException e) {
            logger.error("error while reading file" + e);
        }

        return result;
    }

    /**
     * gets snippets after the result is generated.
     * @return List with code of student one as first value and rest of the values
     * are the lines in student two's code that are similar to student one.
     */
    @Override
    public List<List<String>> getAllSnippets() {
        return snippets;
    }

    /**
     * calculates the percentage and generates snippet.
     * @param astList1 ast list for student1
     * @param astList2 ast list for student2
     */
    private void calculateLCS(List<List<Integer>> astList1, List<List<Integer>> astList2) {

        int length1 = astList1.size();
        int length2 = astList2.size();
        int L[][] = new int[length1 + 1][length2 + 1];
        for (int i=0; i<=length1; i++)
        {
            for (int j=0; j<=length2; j++)
            {
                if (i == 0 || j == 0)
                    L[i][j] = 0;
                else if (astList1.get(i-1).get(0) == astList2.get(j-1).get(0))
                    L[i][j] = L[i-1][j-1] + 1;
                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]);
            }
        }

        double avgLength = (length1 + length2) / 2.0;
        if (avgLength > 0.0) {
            result =  (L[length1][length2]*100)/avgLength;
            DecimalFormat df = new DecimalFormat("#.##");
            result =  Double.parseDouble(df.format(result));
        }

        getSnippets(L, astList1, astList2);
    }

    /**
     * Get snippets from asts
     * @param L LCS array
     * @param astList1 ast list for student 1
     * @param astList2 ast list for student 2
     */
    private void getSnippets(int [][] L, List<List<Integer>> astList1, List<List<Integer>> astList2) {
        int i = astList1.size();
        int j = astList2.size();

        Map<Integer, Set<Integer>> snippetLine = new HashMap<>();

        while (i > 0 && j > 0) {
            if (astList1.get(i-1).get(0) == astList2.get(j-1).get(0)) {
                Integer line1 = astList1.get(i-1).get(1);
                Integer line2 = astList2.get(j-1).get(1);
                if (snippetLine.containsKey(line1)) {
                    snippetLine.get(line1).add(line2);
                } else {
                    Set lineNo = new HashSet<Integer>();
                    lineNo.add(line2);
                    snippetLine.put(line1, lineNo);
                }
                i--; j--;
            }

            else if (L[i-1][j] > L[i][j-1])
                i--;
            else
                j--;
        }

        getLines(snippetLine);
    }

    /**
     * get lines and add to snippet.
     * @param snippetLine snippet map.
     */
    private void getLines(Map<Integer, Set<Integer>> snippetLine) {
        try {
            List<String> allLinesStudent1 = Files.readAllLines(Paths.get(
                    homeworkPath + student1 + "/mainAfterMerge.py"));
            List<String> linesInfoStudent1 = Files.readAllLines(Paths.get(
                    homeworkPath + student1 + "/lineNoAfterMerge.txt"));
            List<String> allLinesStudent2 = Files.readAllLines(Paths.get(
                    homeworkPath + student2 + "/mainAfterMerge.py"));
            List<String> linesInfoStudent2 = Files.readAllLines(Paths.get(
                    homeworkPath + student2 + "/lineNoAfterMerge.txt"));

            if (linesInfoStudent1.size() < 2 && linesInfoStudent2.size() < 2)
                return;

            for(Map.Entry<Integer, Set<Integer>> entry : snippetLine.entrySet()) {
                Integer lineNo1 = entry.getKey();
                String highlightLine;
                List<String> value = new ArrayList<>();
                lineNo1 = lineNo1 - 1;
                if (lineNo1 < allLinesStudent1.size()) {
                    String getContextStudent1 = getContext(allLinesStudent1, linesInfoStudent1, lineNo1);
                    highlightLine = allLinesStudent1.get(lineNo1);
                    value.add(getContextStudent1);
                    for (Integer lineNo2 : entry.getValue()) {
                        lineNo2 = lineNo2 - 1;
                        if (lineNo2 < allLinesStudent2.size()) {
                            String line2 = allLinesStudent2.get(lineNo2);

                            Boolean isSimilar = checkSimilaritySnippets(highlightLine, line2);
                            if (isSimilar) {
                                String getContextStudent2 = getContext(allLinesStudent2, linesInfoStudent2, lineNo2);
                                value.add(getContextStudent2);
                            }
                        }
                    }
                    if (value.size() > 1) {
                        snippets.add(value);
                    }
                }
            }
        } catch (IOException e1) {
            logger.error("Exception while reading the file" + e1);
        }
    }

    /**
     * Check if two lines are similar
     * @param line1 first line
     * @param line2 second line
     * @return return if two lines has one token similar.
     */
    private boolean checkSimilaritySnippets(String line1, String line2) {
        String[] line1Tokens = line1.trim().split("[\\W]+");
        String[] line2Tokens = line2.trim().split("[\\W]+");
        for (String token1: line1Tokens) {
            for (String token2: line2Tokens) {
                if (token1.equals(token2))
                    return true;
            }
        }

        return false;
    }

    /**
     * Gets context of the line
     * @param allLinesStudent lines from mainAfterMerge
     * @param linesInfoStudent lines from lineNoAfterMerge
     * @param lineNo line whose context is to be found
     * @return returns context of the line number
     */
    private String getContext(List<String> allLinesStudent, List<String> linesInfoStudent, Integer lineNo) {
        int lineIndex = 1;
        String file = "";
        String lineAbove = "";
        String lineBelow = "";
        String highlightLine;
        String context;
        while (file == ""  || lineIndex + 1 < allLinesStudent.size()) {
            Integer lineNo1 = Integer.parseInt(linesInfoStudent.get(lineIndex));
            Integer lineNo2 = Integer.parseInt(linesInfoStudent.get(lineIndex + 1));
            Integer lineCheckIndex = lineNo + 1;
            if (lineNo1 <= lineCheckIndex && lineCheckIndex <= lineNo2) {
                file = linesInfoStudent.get(lineIndex - 1);
                context = "<Filename>" + file + "</Filename>";
                if (lineCheckIndex != lineNo1) {
                    lineAbove = allLinesStudent.get(lineNo - 1);
                }
                highlightLine = allLinesStudent.get(lineNo);
                if (lineCheckIndex != lineNo2) {
                    lineBelow = allLinesStudent.get(lineNo + 1);
                }
                context += "<lines>" + lineAbove + "<mark>" + highlightLine + "</mark>" + lineBelow + "</lines>";
                return context;
            }
            lineIndex += 3;
        }

        return "";
    }
}
