package comparison;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Plagiarism results for document fingerprinting.
 */
public class PlagiarismFingerPrintingTests {
    ComparisonTechniques fingerprinting = new DocumentFingerprinting();
    /**
     * Test similarity of nodes.
     */
    @Test
    public void testSimilarityOfNodes() {
        double result  = fingerprinting.getResult(
                "src/main/homeworks/HW1/", "Student-1", "Student-2");
        assertEquals(47.94 , result, 5.00);
    }

    /**
     * Test similarity of same node.
     */
    @Test
    public void testSimilarityOfSameNodes() {
        double result  = fingerprinting.getResult(
                "src/main/homeworks/HW1/", "Student-1", "Student-1");
        assertEquals( 100.0 ,result, 5.00);
    }

    /**
     * Test variation of code with comments.
     */
    @Test
    public void testVariationWithComments() {
        double result  = fingerprinting.getResult(
                "src/main/homeworks/HW3/", "Student-2", "Student-4");
        assertEquals( 42.98 , result, 5.00);
    }

    /**
     * Test dissimilar code.
     */
    @Test
    public void testDissimilarCode() {

        double result  = fingerprinting.getResult(
                "src/main/homeworks/HW1/", "Student-1", "Student-3");
        assertEquals( 0.0 ,result,  0.001);
    }
}
