package service.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AssignmentDao;
import model.Assignment;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.assignments.AssignmentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for HomeService for all operations related to homework
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class AssignmentServiceTest {
    private MockMvc mvc;
    private Assignment assignmentExample;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentDao assignmentDao;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(assignmentService).build();
        assignmentExample= new Assignment();
    }

    /**
     * Test add all assignments
     */
    @Test
    public void addAllAssignmentsTest() {
        List<String> homeworks = new ArrayList<>();
        homeworks.add("HW1");
        homeworks.add("HW2");
        List<Assignment> hws = new ArrayList<>();
        hws.add(assignmentExample);
        when(assignmentDao.addAllAssignments("1", homeworks)).thenReturn(hws);
        assertEquals(assignmentService.addAllAssignments("1",homeworks),hws);
    }

    /**
     * Test add all assignments, by adding no assignments
     */
    @Test
    public void addAllAssignmentsTestNull() {
        List<String> homeworks = new ArrayList<>();
        homeworks.add("HW1");
        homeworks.add("HW2");
        List<Assignment> hws = new ArrayList<>();
        hws.add(null);
        when(assignmentDao.addAllAssignments("1", homeworks)).thenReturn(hws);
        assertEquals(assignmentService.addAllAssignments("1",homeworks),hws);
    }

    /**
     * Test get assignment by course id
     */
    @Test
    public void getAssignmentTest() {
        List<Assignment> hws = new ArrayList<>();
        hws.add(assignmentExample);
        when(assignmentDao.getAssignment("1")).thenReturn(hws);
        assertEquals(hws,assignmentService.getAssignment("1"));
    }

    /**
     * Test get assignment by course id not containing the assignment
     */
    @Test
    public void getAssignmentTestNull() {
        List<Assignment> hws = new ArrayList<>();
        hws.add(null);
        when(assignmentDao.getAssignment("1")).thenReturn(hws);
        assertEquals(hws,assignmentService.getAssignment("1"));
    }

    /**
     * Test to store results
     */
    @Test
    public void storeResultsTest() {
        List<Result> results = new ArrayList<>();
        List<Result> results1 = new ArrayList<>();
        Result newresult = new Result();
        newresult.setStudent1("Student-1");
        newresult.setStudent2("Student-2");
        newresult.setPercentage(98.00);
        results.add(newresult);
        when(assignmentDao.storeResults(results1, "1")).thenReturn(results);
        assertEquals(results,assignmentService.storeResults(results1, "1"));
    }

    /**
     * Test to store results with null results
     */
    @Test
    public void storeResultsTestNull() {
        List<Result> results1 = new ArrayList<>();
        results1=null;
        when(assignmentDao.storeResults(results1, "1")).thenReturn(null);
        assertEquals(null,assignmentService.storeResults(results1, "1"));
    }

    /**
     * Test to retrieve the stored results
     */
    @Test
    public void getResultsTest() {
        List<Result> results = new ArrayList<>();
        Result newresult = new Result();
        newresult.setStudent1("Student-1");
        newresult.setStudent2("Student-2");
        newresult.setPercentage(98.00);
        results.add(newresult);
        when(assignmentDao.getResults("1")).thenReturn(results);
        assertEquals(results,assignmentService.getResults("1"));
    }

    /**
     * Test to retrieve the stored results, when no results are stored
     */
    @Test
    public void getResultsTestNull() {
        when(assignmentDao.getResults("1")).thenReturn(null);
        assertEquals(null,assignmentService.getResults("1"));
    }
}