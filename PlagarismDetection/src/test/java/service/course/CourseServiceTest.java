package service.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CourseDaoImpl;
import dao.InstructorDaoImpl;
import jgit.JgitException;
import jgit.JgitImpl;
import model.Assignment;
import model.Course;
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
import service.courses.CourseServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * CourseServiceTest : Tests for All services related to courses
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceTest {
    private MockMvc mvc;
    private Course courseExample;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseDaoImpl courseDao;

    @Mock
    private JgitImpl jgit;

    @Mock
    private InstructorDaoImpl instructorDao;

    @Mock
    private AssignmentServiceImpl assignmentService;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(assignmentService).build();
        courseExample=new Course();
    }

    /**
     * Test get the list of courses by user id
     */
    @Test
    public void getCoursesTest() {
        List<Course> courses = new ArrayList<>();
        courses.add(courseExample);
        when(courseDao.getCourses("1")).thenReturn(courses);
        assertEquals(courseService.getCourses("1"), courses);
    }

    /**
     * Test get the list of courses by user id, when the user has no course
     */
    @Test
    public void getCoursesTestNull() {
        List<Course> courses = null;
        when(courseDao.getCourses("1")).thenReturn(courses);
        assertEquals(courseService.getCourses("1"), courses);
    }

    /**
     * Test add course by user id
     */
    @Test
    public void addCourseTest() throws JgitException {
        List<Assignment> assignmentList = new ArrayList<>();
        Assignment newAssignment = new Assignment();
        newAssignment.setName("HW1");
        List<String> homeworks = new ArrayList<>();
        homeworks.add("HW1");
        Course newCourse = new Course();
        newCourse.setHexid("1");
        assignmentList.add(newAssignment);
        when(assignmentService.addAllAssignments("1", homeworks)).thenReturn(assignmentList);
        when(courseDao.addCourse(newCourse, "1", assignmentList)).thenReturn(newCourse);
        assertEquals(null,courseService.addCourse(newCourse, "1"));
    }

    /**
     * Test get course by user id
     */
    @Test
    public void getCourseByIdTest() {
        Course c = new Course();
        c.setHexid("1");
        when(courseDao.getCourseByCourseId("1")).thenReturn(c);
        assertEquals(c,courseService.getCourseById("1"));
    }

    /**
     * Test get course by user id, when the user has no course
     */
    @Test
    public void getCourseByIdNullTest() {
        Course c = null;
        when(courseDao.getCourseByCourseId("1")).thenReturn(c);
        assertEquals(courseService.getCourseById("1"), c);
    }

}
