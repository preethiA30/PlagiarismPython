package comparison;
import percentage.Percentage;

/**
 * PlagiarismDetector interface will use a visitor pattern to accept the Visitor and dispatch the
 * implementing class which will contain 2 AST to find similarity between them
 */
public interface PlagiarismDetector {
    /**
     *
     * @param c A Visitor which will have a implementation class
     * @return a Percentage generated of the 2 AST
     */
    Percentage validComparison(ComparisonVisitor c);
}
