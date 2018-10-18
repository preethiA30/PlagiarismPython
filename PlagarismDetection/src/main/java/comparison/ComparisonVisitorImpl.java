package comparison;

import ast.AST;
import ast.ASTCreatorImpl;
import ast.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import percentage.Percentage;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ComparisonVisitorImpl implements ComparisonVisitor, ComparisonTechniques {
    /**
     * @param pd a plagiarismDetector object of type LongestCommonSubsequence Strategy
     * @return a Percentage specifying the similarity between two nodes
     */
    @Override
    public Percentage findPlagiarism(PlagiarismDetectorLCS pd) {
        Node child1 = pd.getChild1();
        Node child2 = pd.getChild2();
        /**
         * Traverse child1in PreOrder format
         */
        List<Node> child1PreOrder = new ArrayList<>();
        preOrderTraversal(child1, child1PreOrder);
        /**
         * Traverse child2in PreOrder format
         */
        List<Node> child2PreOrder = new ArrayList<>();
        preOrderTraversal(child2, child2PreOrder);
        int child1Length = child1PreOrder.size();
        int child2Length = child2PreOrder.size();
        /**
         * Genrate Longest Common Subsequence score matrix
         */
        int[][] childNodeScore = generateLCS(child1PreOrder, child2PreOrder);
        /**
         * Fetch the final score which represents the LCS length
         */
        int childScore = childNodeScore[childNodeScore.length - 1][childNodeScore[0].length - 1];
        /**
         * Find the nodes which are common  by aligning the 2 sequence
         */
        findCommonNodes(childNodeScore, child1PreOrder, child2PreOrder);
        /**
         * Calculate similarity score and return report
         */
        float simScore = comparisonSimilarity(childScore, child1Length, child2Length);
        return new Percentage(simScore);
    }

    /**
     * The function will take traverse a node in pre-order fashion and keep adding in the preOrderChildNode list
     *
     * @param node              the current Node which needs to be traversed
     * @param preOrderChildNode the list will contain the pre-order traversed node till the current Node
     */
    private void preOrderTraversal(Node node, List<Node> preOrderChildNode) {
        if (node == null)
            return;
        /**
         * add the Root
         */
        preOrderChildNode.add(node);
        /**
         * Traverse on child, retrieve children and recurse
         */
        for (Node child : node.getChildren()) {
            preOrderTraversal(child, preOrderChildNode);
        }
    }

    /**
     * @param child1PreOrder the child1traversed in preorder format
     * @param child2PreOrder the child2traversed in preorder format
     * @return final matrix which stores the LCS of the 2 Nodes passed
     */
    private int[][] generateLCS(List<Node> child1PreOrder, List<Node> child2PreOrder) {
        int child1Len = child1PreOrder.size() + 1;
        int child2Len = child2PreOrder.size() + 1;
        int[][] childNodeScore = new int[child1Len][child2Len];
        /**
         * Fill the LCS matrix
         */
        for (int i = 1; i < child1Len; i++) {
            /**
             * Fetch the child1node and rule name which would be compared with all
             * the child2rule names to fill the matrix
             */
            Node child1 = child1PreOrder.get(i - 1);
            String rulenameChild1 = child1.getName();
            for (int j = 1; j < child2Len; j++) {
                /**
                 * Fetch the child2rule name and compare with the child1rulename
                 */
                Node child2 = child2PreOrder.get(j - 1);
                String rulenameChild2 = child2.getName();
                int curScore = 0;
                /**
                 * If the rule names for child1and child2match, increase the score by 1 and fill
                 * Else take the maximum score from the immediate left or immediate topelement
                 */
                if (rulenameChild1.equals(rulenameChild2)) {
                    curScore = childNodeScore[i - 1][j - 1] + 1;
                } else {
                    curScore = Math.max(childNodeScore[i][j - 1], childNodeScore[i - 1][j]);
                }
                /**
                 * Update the current score
                 */
                childNodeScore[i][j] = curScore;
            }
        }
        return childNodeScore;
    }

    /**
     * @param childNodeScore the matrix which stores the LCS scores
     * @param child1PreOrder child1traversed in pre order format
     * @param child2PreOrder child2traversed in pre order format
     */
    private void findCommonNodes(int[][] childNodeScore, List<Node> child1PreOrder, List<Node> child2PreOrder) {
        int i = childNodeScore.length - 1;
        int j = childNodeScore[0].length - 1;
        /**
         * Start from the bottom right last cell of the matrix and backtrack unitl we
         * reach the top left cell
         */
        while (!(i <= 0 && j <= 0)) {
            int i1 = i == 0 ? 0 : i - 1;
            int j1 = j == 0 ? 0 : j - 1;
            /**
             * Get the scores which are filled on the left, topand diagonalonal of the current cell
             */
            int left = childNodeScore[i][j1];
            int top = childNodeScore[i1][j];
            int diagonal = childNodeScore[i1][j1];
            /**
             * Use the diagonalonal score if it is the maximum
             */
            if (diagonal >= left && diagonal >= top) {
                i = i - 1;
                j = j - 1;
            }
            /**
             * Use the score on the upper cell if it is larger than left cell but smaller than diagonalonal cell
             * Add null value to align
             */
            else if (top > left) {
                child2PreOrder.add(j, new Node("Null"));
                i = i - 1;
            }
            /**
             * Use the left cell otherwise
             * Add null value to align
             */
            else {
                child1PreOrder.add(i, new Node("Null"));
                j = j - 1;
            }
        }
    }

    /**
     * @param childScore   the size of longest common subsequence
     * @param child1Length the size of child1tree length
     * @param child2Length the size of child2tree length
     * @return the final similarity score
     */
    private float comparisonSimilarity(int childScore, int child1Length, int child2Length) {
        float totalNodes = (float) child1Length + child2Length;
        return (2 * childScore) / totalNodes;
    }

    /**
     * Get comparison strategies result.
     *
     * @param homework Homework for which plagiarism is to be run.
     * @param student1 directory name of first student.
     * @param student2 directory name of second student.
     * @return result
     */
    @Override
    public double getResult(String homework, String student1, String student2) {
        ASTCreatorImpl parserFacade = new ASTCreatorImpl();
        AST ast;
        try {
            ast = new AST(parserFacade.parse(new File(homework + student1 + "/mainAfterMerge.py")));

            Node childNode = ast.getAstNode();
            AST ast1 = new AST(parserFacade.parse(new File(homework + student2 + "/mainAfterMerge.py")));

            Node childNode1 = ast1.getAstNode();
            ComparisonVisitor cv = new ComparisonVisitorImpl();
            PlagiarismDetectorLCS pd = new PlagiarismDetectorLCS(childNode, childNode1);
            Percentage rep = pd.validComparison(cv);
            double value = rep.getPercentage() * 100;
            DecimalFormat df = new DecimalFormat("#.##");

            return Double.parseDouble(df.format(value));
        } catch (IOException e) {
            logger.error("Exception in Ast" + e);
        }
        return 0.0;
    }

    /**
     * gets snippets after the result is generated.
     * @return List with code of student one as first value and rest of the values
     * are the lines in student two's code that are similar to student one.
     */
    @Override
    public List<List<String>> getAllSnippets() {
        return new ArrayList<>();
    }

    /**
     * Add a new course
     */
    private static Logger logger = LogManager.getLogger();


}
