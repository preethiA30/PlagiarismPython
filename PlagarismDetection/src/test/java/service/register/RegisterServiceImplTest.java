package service.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.InstructorDaoImpl;
import model.User;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.login.LoginServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/23/18
 * @email thakker.m@husky.neu.edu
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterServiceImplTest {

    private MockMvc mvc;


    /**
     * Register service
     */
    @InjectMocks
    private RegisterServiceImpl registerService;


    @Mock
    private InstructorDaoImpl instructorDao;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(registerService).build();
    }


    /**
     * Register a user with valid credential
     */

    @Test
    public void registerTest() throws DuplicateUsernameException {
        User user = new User("manthan", "thakker", "git", "pass");
        when(instructorDao.createUser("manthan", "thakker", "git", "pass")).thenReturn(user);
        assertEquals(registerService.registerNewUser("manthan", "thakker", "git", "pass"), user);
    }


    /**
     * Register a user with no git Id and password
     */
    @Test
    public void registerTestWithNoGit() throws DuplicateUsernameException {
        User user = new User("manthan", "thakker", "", "");
        when(instructorDao.createUser("manthan", "thakker", "", "")).thenReturn(user);
        assertEquals(registerService.registerNewUser("manthan", "thakker", "", ""), user);
    }

    /**
     * Register a username with no name
     */
    @Test
    public void registerTestWithNoName() throws DuplicateUsernameException {
        User user = new User("", "thakker", "", "");
        when(instructorDao.createUser("", "thakker", "", "")).thenReturn(user);
        assertEquals(registerService.registerNewUser("", "thakker", "", ""), user);
    }
}