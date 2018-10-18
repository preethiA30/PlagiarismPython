package service.plagiarism;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CounterDaoImpl;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.counter.CounterServiceImpl;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for plagiarism results.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlagiarismTest {

    private MockMvc mvc;

    @InjectMocks
    private CounterServiceImpl counterService;

    @Mock
    CounterDaoImpl counterDao;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(counterService).build();
        counterService=new CounterServiceImpl(counterDao);
    }
    PlagiarismResultImp pr = new PlagiarismResultImp();




    /**
     * Testing Project for plagiarism using LCS
     */
    @Test
    public void testSimilarityOfHomeworksLCS() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW1/", Strategy.LCS, 1, counterService);
        Result r1 = new Result();
        r1.setStudent1("Student-1");
        r1.setStudent2("Student-2");
        r1.setPercentage(94.68);

        List<Result> actual = Arrays.asList(r1);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(true, actual.get(i).equals(results.get(i)));
        }
    }

    /**
     * Testing project for plagiarism using Edit Distance
     */
    @Test
    public void testSimilarityOfHomeworksEditDistance() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW3/", Strategy.EDIT_DISTANCE, 1, counterService);
        Result r1 = new Result();
        r1.setStudent1("Student-2");
        r1.setStudent2("Student-4");
        r1.setPercentage(56.58);

        List<Result> actual = Arrays.asList(r1);

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(true, actual.get(i).equals(results.get(i)));
        }
    }

    /**
     * Testing project for plagiarism using Document Finger Printing
     */
    @Test
    public void testSimilarityOfHomeworksFingerPrint() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW2/", Strategy.FINGERPRINT, 1, counterService);
        Result r1 = new Result();
        r1.setStudent1("Student-1");
        r1.setStudent2("Student-2");
        r1.setPercentage(71.62);

        List<Result> actual = Arrays.asList(r1);

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(true, actual.get(i).equals(results.get(i)));
        }
    }

    /**
     * Testing project for plagiarism using Document Finger Printing
     */
    @Test
    public void testSimilarityOfHomeworksNoStrategy() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW4/", Strategy.ALL, 1, counterService);
        Result r1 = new Result();
        r1.setStudent1("Student-3");
        r1.setStudent2("Student-4");
        r1.setPercentage(11.92);

        List<Result> actual = Arrays.asList(r1);

        for (int i = 0; i < actual.size(); i++) {
           assertEquals(true, actual.get(i).equals(results.get(i)));
        }
    }

    /**
     * Test similarity of same homeworks of no students using Edit Distance
     */
    @Test
    public void testSimilarityOfNoStudentsEditDist() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW5/", Strategy.EDIT_DISTANCE, 0, counterService);
        List<List<String>> actual = Arrays.asList();

        assertEquals(results, actual);
    }

    /**
     * Test similarity of same homeworks of no students using LCS
     */
    @Test
    public void testSimilarityOfNoStudentsLCS() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW6/", Strategy.LCS, 0, counterService);
        List<List<String>> actual = Arrays.asList();
        assertEquals(results, actual);
    }

    /**
     * Test similarity of same homeworks of no students using Document Finger Printing
     */
    @Test
    public void testSimilarityOfNoStudentsFingerprint() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW3/", Strategy.FINGERPRINT, 0, counterService);
        List<List<String>> actual = Arrays.asList();
        assertEquals(results, actual);
    }

    /**
     * Testing project with no plagiarism
     */
    @Test
    public void testNoPlagiarism() {
        List<Result> results = pr.generateResults(
                "src/main/homeworks/HW7/", Strategy.FINGERPRINT, 20, counterService);

        Result r1 = new Result();

        r1.setStudent1("Student-1");
        r1.setStudent2("Student-2");
        r1.setPercentage(0.0);

        List<Result> actual = Arrays.asList(r1);

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(true, actual.get(i).equals(results.get(i)));
        }
    }

    /**
     * Testing project with no plagiarism
     */
    @Test
    public void testForPlagiarism() {
        List<Result> results = pr.generateResults(
                "src/main/homeworkss/HW1/", Strategy.LCS, 20, counterService);

        Result r1 = new Result();

        r1.setStudent1("Student-5");
        r1.setStudent2("Student-3");
        r1.setPercentage(95.77);

        List<Result> actual = Arrays.asList(r1);

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(false, actual.get(i).equals(results.get(i)));
        }
    }
}