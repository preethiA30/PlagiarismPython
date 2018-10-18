package comparison;
import ast.Node;
import percentage.Percentage;

public class PlagiarismDetectorLCS implements PlagiarismDetector {
    /**
     * Nodes which needs to be compared for Similarity
     */
    private Node child1;
    private Node child2;
    /**
     *
     * @param child1 Node to detect LCS simlarity
     * @param child2 Node to detect LCS similarity
     */
    public PlagiarismDetectorLCS(Node child1, Node child2){
        this.child1= child1;
        this.child2= child2;
    }
    /**
     * Getter for child1
     * @return the First Node
     */
    public Node getChild1() {
        return child1;
    }
    /**
     * Getter for child2
     * @return the Second Node
     */
    public Node getChild2() {
        return child2;
    }
    /**
     * Use Visitor pattern to detect LCS similarity
     * @param c A Visitor which will have a implementation class
     * @return a Percentage saying the similarity of child1with child2
     */
    @Override
    public Percentage validComparison(ComparisonVisitor c) {
        return  c.findPlagiarism(this);
    }
}
