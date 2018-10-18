package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import model.Credentials;
import model.User;
import org.bson.Document;
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
import service.login.LoginService;
import service.register.DuplicateUsernameException;
import service.register.RegisterService;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Manthan Thakker
 * @project PlagarismDetection
 * @date 3/24/18
 * @email thakker.m@husky.neu.edu
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    private MockMvc mvc;

    @InjectMocks
    UserController userController;

    @Mock
    LoginService loginService;

    @Mock
    RegisterService registerService;


    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }


    @Test
    public void getlogin() {
        User user = new User();
        Document userDoc = new Document();

        when(loginService.login("usn", "pass")).thenReturn(userDoc);

        String userJson = new Gson().toJson(user);
        try {
            MockHttpServletResponse response = mvc.perform(
                    post("/api/login").contentType(MediaType.APPLICATION_JSON).content(
                            userJson
                    )).andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void registerUser() throws DuplicateUsernameException {

        User user = new User();

        when(registerService.registerNewUser("usn", "pass", "git", "pass")).thenReturn(user);

        String userJson = new Gson().toJson(user);
        try {
            MockHttpServletResponse response = mvc.perform(
                    post("/api/register").contentType(MediaType.APPLICATION_JSON).content(
                            userJson
                    )).andReturn().getResponse();

            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}