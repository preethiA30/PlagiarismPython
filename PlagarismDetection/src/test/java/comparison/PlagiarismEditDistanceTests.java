package comparison;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for edit distance strategy.
 */
public class PlagiarismEditDistanceTests {
    ComparisonTechniques editDistance = new EditDistanceImpl();

    /**
     * Test similarity of different nodes.
     */
    @Test
    public void testSimilarityOfNodes() {
        double result  = editDistance.getResult(
                "src/main/homeworks/HW1/", "Student-1", "Student-2");
        assertEquals(75.27, result , 0.01);
    }

    /**
     * Test similarity of same nodes.
     */
    @Test
    public void testSimilarityOfSameNodes() {
        double result  = editDistance.getResult(
                "src/main/homeworks/HW1/", "Student-1", "Student-1");
        assertEquals(100.0 , result,  5.00);
    }

    /**
     * Test variation of code with comments.
     */
    @Test
    public void testVariationWithComments() {
        double result  = editDistance.getResult(
                "src/main/homeworks/HW2/", "Student-1", "Student-2");
        assertEquals( 71.62, result,  5.00);
    }

    /**
     * Test dissimilar code.
     */
    @Test
    public void testDissimilarCode() {
        double result  = editDistance.getResult(
                "src/main/homeworks/HW5/", "Student-1", "Student-3");
        assertEquals( 15.05, result, 5.00);
    }
}
