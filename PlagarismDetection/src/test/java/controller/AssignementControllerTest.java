package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Assignment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.assignments.AssignmentService;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/24/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Assignment Controller tests to map all tests from assignment page
 */
@RunWith(MockitoJUnitRunner.class)
public class AssignementControllerTest {

    /**
     * Mock Setup
     */
    private MockMvc mvc;

    /**
     * Controller service to test
     */
    @InjectMocks
    private AssignementController assignementController;

    /**
     * Service to Mock
     */
    @Mock
    private AssignmentService assignmentService;


    /**
     * Setting up environment
     */
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(assignementController)
                .build();
    }


    /**
     * Get results for Assignment
     */
    @Test
    public void getResultsForAssignment() {

        try {
            MockHttpServletResponse response = mvc.perform(
                    get(" /api/getCourse").accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testForNoAssignments() {

        List<Assignment> noAssignments = new LinkedList<>();
        when(assignmentService.getAssignment("courseId")).thenReturn(noAssignments);
        try {
            MockHttpServletResponse response = mvc.perform(
                    get(" api/course/courseId/assignment").accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateResults() {

        List<Assignment> noAssignments = new LinkedList<>();
        when(assignmentService.getAssignment("courseId")).thenReturn(noAssignments);
        try {
            MockHttpServletResponse response = mvc.perform(
                    get(" api/course/courseId/assignment").accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testForDeleteRepo() {
        File file = new File("HW1");

        assertNotNull(assignementController.deleteRepo("HW1"));
    }
}