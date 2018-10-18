package comparison;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for LCS.
 */
public class PlagiarismDetectorLCSTests {

    /**
     * Test for almost same code
     * @throws IOException
     */
    @Test
    public void test_Match() throws IOException {
        ComparisonVisitorImpl c = new ComparisonVisitorImpl();
        c.getResult("src/main/homeworks/HW1/","Student-1", "Student-2");
        assertEquals("Similarity Should be 94.74", 94.69,
                c.getResult("src/main/homeworks/HW1/","Student-1", "Student-2") , 0.0);

    }
    /**
     * Test for dissimilar code
     * @throws IOException
     */
    @Test
    public void test_NoMatch() throws IOException {
        ComparisonVisitorImpl c = new ComparisonVisitorImpl();
        c.getResult("src/main/homeworks/HW2/","Student-1", "Student-2");
        assertEquals("Similarity Should be 49.12", 92.17,
                c.getResult("src/main/homeworks/HW2/","Student-1", "Student-2") , 0.0);
    }

    /**
     * Test similarity of same node.
     */
    @Test
    public void testSimilarityOfSameNodes(){
        ComparisonVisitorImpl c = new ComparisonVisitorImpl();
        c.getResult("src/main/homeworks/HW3/","Student-2", "Student-2");
        assertEquals("Similarity Should be 100", 100.0,
                c.getResult("src/main/homeworks/HW3/","Student-2", "Student-2") , 0.0);
    }

    /**
     * Test variation of comments
     */
    @Test
    public void testVariationOfComments(){
        ComparisonVisitorImpl c = new ComparisonVisitorImpl();
        c.getResult("src/main/homeworks/HW4/","Student-3", "Student-4");
        assertEquals("Similarity Should be 78.01", 42.24,
                c.getResult("src/main/homeworks/HW4/","Student-3", "Student-4") , 0.0);
    }

    /**
     * Test dissimilar code.
     */
    @Test
    public void testDissimilarCode(){
        ComparisonVisitorImpl c = new ComparisonVisitorImpl();
        c.getResult("src/main/homeworks/HW5/","Student-1", "Student-3");
        assertEquals("Similarity Should be 51.16", 51.76,
                c.getResult("src/main/homeworks/HW5/","Student-1", "Student-3") , 0.0);
    }


}