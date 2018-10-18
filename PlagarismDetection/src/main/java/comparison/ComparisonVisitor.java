package comparison;
import percentage.Percentage;

public interface ComparisonVisitor {
    /**
     *
     * @param pd a PlagiarismDetector object of type LongestCommonSubsequence Strategy
     * @return a Percentage generated after running similarity detection Algorithm
     */
    Percentage findPlagiarism(PlagiarismDetectorLCS pd);

}
