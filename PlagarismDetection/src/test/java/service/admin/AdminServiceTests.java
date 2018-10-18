package service.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.InstructorDaoImpl;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTests {
    /**
     * ModelMvc Spring Boot
     */
    private MockMvc mvc;
    private User userExample;


    /**
     * Login Service Mock to be tested
     */
    @InjectMocks
    private AdminServiceImpl adminService;


    /**
     * Mock created for database access to the instructor
     */
    @Mock
    private InstructorDaoImpl instructorDao;


    /**
     * Setup to initialize the mocks
     */
    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(adminService).build();
        userExample= new User();
    }
    /**
     * Getting all users
     */
    @Test
    public void testGetAllUsers() {

        List<String> user = new ArrayList<>();
        user.add("Preethi");
        user.add("Preeths");
        List<User> users = new ArrayList<>();
        users.add(userExample);
        when(instructorDao.getAllUsers()).thenReturn(users);
        assertEquals(adminService.getAllUsers(),users);
    }

    /**
     * Deleting all users
     */
    @Test
    public void testDeleteAllUsers() {

        List<String> user = new ArrayList<>();
        user.add("Preethi");
        user.add("Preeths");
        List<User> users = new ArrayList<>();
        users.add(userExample);
        when(instructorDao.deleteUserByUserId("1")).thenReturn(true);
        assertEquals(adminService.deleteUser("1"),true);
    }


}

