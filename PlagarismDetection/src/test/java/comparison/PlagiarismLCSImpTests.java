package comparison;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests for LCS implementation with snippet generation.
 */
public class PlagiarismLCSImpTests {
    ComparisonTechniques lscImp = new LCSImp();
    /**
     * Test similarity of nodes.
     */
    @Test
    public void testSimilarityOfNodes() {
        double result  = lscImp.getResult(
                "src/main/homeworkss/HW1/", "Student-1", "Student-2");
        assertEquals(95.77 , result, 5.00);
    }

    /**
     * Test similarity of same node.
     */
    @Test
    public void testSimilarityOfSameNodes() {
        double result  = lscImp.getResult(
                "src/main/homeworkss/HW1/", "Student-1", "Student-1");
        assertEquals( 100.0 ,result, 5.00);
    }

    /**
     * Test variation of code with comments.
     */
    @Test
    public void testVariationWithComments() {
        double result  = lscImp.getResult(
                "src/main/homeworkss/HW1/", "Student-1", "Student-4");
        assertEquals( 78.41 , result, 5.00);
    }

    /**
     * Test dissimilar code.
     */
    @Test
    public void testDissimilarCode() {

        double result  = lscImp.getResult(
                "src/main/homeworkss/HW1/", "Student-1", "Student-3");
        assertEquals( 40.74 ,result,  0.001);
    }

    /**
     * Test snippet generation.
     */
    @Test
    public void testSnippetGeneration() {
        double result  = lscImp.getResult(
                "src/main/homeworkss/HW1/", "Student-5", "Student-3");
        List<List<String>> snippets = lscImp.getAllSnippets();

        List<List<String>> expectedSnippet = new ArrayList<>();

        List<String> snippet1 = new ArrayList<>();
        snippet1.add("<Filename>studentA.py</Filename><lines><mark>print(\"The mul of %i and %i is %i\" % (5, 3, sum(5, 3)))</mark></lines>");
        snippet1.add("<Filename>studentB.py</Filename><lines>for i, name in enumerate(friends):<mark>    print \"iteration {iteration} is {name}\".format(iteration=i, name=name)</mark></lines>");
        expectedSnippet.add(snippet1);

        assertEquals(expectedSnippet, snippets);
        assertEquals(43.24 , result, 5.00);
    }
}
