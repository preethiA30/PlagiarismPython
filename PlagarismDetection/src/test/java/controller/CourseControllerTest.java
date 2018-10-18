package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.Course;
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
import service.courses.CourseService;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/24/18
 * @email thakker.m@husky.neu.edu
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest {


    /**
     * Mock Setup
     */
    private MockMvc mvc;


    @InjectMocks
    private CourseController courseController;


    @Mock
    private CourseService courseService;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(courseController)
                .build();
    }


    @Test
    public void testForgetCourseForUserId() {
        Course course = new Course();

        when(courseService.getCourseById("1234")).thenReturn(course);

        try {
            MockHttpServletResponse response = mvc.perform(
                    get("api/course/1234/assignment").accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void newCourse() {
    }
}