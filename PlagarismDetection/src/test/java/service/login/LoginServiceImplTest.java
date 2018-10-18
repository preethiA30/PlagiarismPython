package service.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.InstructorDaoImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/23/18
 * @email thakker.m@husky.neu.edu
 */

/**
 * Test suite to test Login Functionality
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginServiceImplTest {

    /**
     * ModelMvc Spring Boot
     */
    private MockMvc mvc;


    /**
     * Login Service Mock to be tested
     */
    @InjectMocks
    private LoginServiceImpl loginService;


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
        mvc = MockMvcBuilders.standaloneSetup(loginService).build();
    }

    /**
     * Login for a valid user
     */
    @Test
    public void loginTest() {
        Document user = new Document().append("id", "123");
        when(instructorDao.verifyCredentials("id", "123")).thenReturn(user);
        assertEquals(loginService.login("id", "123"), user);
    }

    /**
     * Login for invalid credentials
     */
    @Test
    public void testLoginForInvalidCredentials() {
        Document user = new Document();
        user.append("username", "username");
        user.append("pass", "hackme");
        when(instructorDao.verifyCredentials("username", "hackme")).thenReturn(null);
        assertEquals(null,loginService.login("username", "hackme") );
    }

    /**
     * Login for invalid credentials, returning null
     */
    @Test
    public void testLoginForInvalidCredentialsTest() {
        Document user = new Document();
        user.append("username", "username");
        user.append("pass", "hackme");
        when(instructorDao.verifyCredentials("username", "hackme")).thenReturn(null);
        assertNotEquals(user,loginService.login("username", "hackme"));
    }

    /**
     * Login for invalid credentials
     */
    @Test
    public void testLoginForEmptyUsername() {
        when(instructorDao.verifyCredentials("", "hackme")).thenReturn(null);
        assertEquals(null,loginService.login("", "hackme"));
    }

    /**
     * Login for invalid credentials, returning null
     */
    @Test
    public void testLoginForEmptyUsernameTest() {
        Document user = new Document();
        user.append("", "hackme");
        when(instructorDao.verifyCredentials("", "hackme")).thenReturn(null);
        assertNotEquals(user,loginService.login("", "hackme"));
    }


    /**
     * Login for invalid credentials
     */
    @Test
    public void testLoginForEmptyPasword() {

        when(instructorDao.verifyCredentials("trisha", "")).thenReturn(null);
        assertEquals(null,loginService.login("trisha", ""));

    }

    /**
     * Login for invalid credentials, returning null
     */
    @Test
    public void testLoginForEmptyPaswordTest() {
        Document user = new Document();
        user.append("trisha", "");
        when(instructorDao.verifyCredentials("trisha", "")).thenReturn(null);
        assertNotEquals(user,loginService.login("trisha", ""));

    }

    /**
     * Login for SQL Injection credentials
     */
    @Test
    public void testLoginForSqlInjection() {
        when(instructorDao.verifyCredentials("username", "DROP USER;")).thenReturn(null);
        assertEquals(null,loginService.login("username", ""));

    }


}


