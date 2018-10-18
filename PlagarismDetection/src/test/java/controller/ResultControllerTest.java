package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;
import model.Result;
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

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/24/18
 * @email thakker.m@husky.neu.edu
 */
@RunWith(MockitoJUnitRunner.class)
public class ResultControllerTest {


    /**
     * Mock Setup
     */
    private MockMvc mvc;

    @InjectMocks
    private ResultController resultController;

    @Mock
    private AssignmentService assignmentService;


    /**
     * Setting up mocks
     */
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(resultController)
                .build();
    }

    /**
     * Test suite for no assignments
     */
    @Test
    public void getResultsForAssignementIdWithNoAssignments() {


        List<Result> listOfResults = new LinkedList<>();

        when(assignmentService.getResults("hw1")).thenReturn(listOfResults);

        try {
            MockHttpServletResponse response = mvc.perform(
                    get("/api/assignment/hw1/result").accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}