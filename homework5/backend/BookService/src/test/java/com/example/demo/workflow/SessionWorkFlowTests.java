package com.example.demo.workflow;

import com.example.demo.dao.UserDAO;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;
import com.example.demo.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionWorkFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionService sessionService;

    @MockBean
    private UserDAO userDAO;

    private ObjectMapper objectMapper;

    @Value("${session.defaultExpiryTime}")
    private int defaultExpiryTime;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
    }

    private LoginResponse performLogin() throws Exception{
        LoginRequest loginRequest = new LoginRequest("hashedUserName","hashedPassword");

        Mockito.when(userDAO.authenticate("hashedUserName","hashedPassword")).thenReturn(true);

        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/api/session/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        LoginResponse loginResponse = objectMapper.readValue(response, LoginResponse.class);
        return loginResponse;
    }


    @Test
    public void testLogout() throws Exception {

        LoginResponse loginResponse = performLogin();

        Assertions.assertNotNull(loginResponse);
        Assertions.assertNotNull(loginResponse.getSessionId());


        Assertions.assertFalse(sessionService.getSessionIdExpiryMap().isEmpty());
        Assertions.assertFalse(sessionService.getSessionIdUserIdMap().isEmpty());
        Assertions.assertFalse(sessionService.getUserIdSessionIdMap().isEmpty());


        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginResponse.getSessionId())))
                .andExpect(MockMvcResultMatchers.status().isOk());


        Assertions.assertTrue(sessionService.getSessionIdExpiryMap().isEmpty());
        Assertions.assertTrue(sessionService.getSessionIdUserIdMap().isEmpty());
        Assertions.assertTrue(sessionService.getUserIdSessionIdMap().isEmpty());

    }


    @Test
    public void testSessionExpiry() throws Exception {

        String testUser = "testuser";
        LoginResponse loginResponse = performLogin();

        Field mapField = SessionService.class.getDeclaredField("sessionIdUserIdMap");
        mapField.setAccessible(true);
        HashMap<String,String> map = (HashMap<String, String>) mapField.get(sessionService);
        Method putMethod = HashMap.class.getMethod("put",Object.class,Object.class);
        putMethod.invoke(map,loginResponse.getSessionId(),testUser);


        Mockito.when(userDAO.isAuthorizedToViewAdminContent(testUser)).thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/")
                        .param("sessionId",loginResponse.getSessionId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Thread.sleep(defaultExpiryTime+1000);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/")
                .param("sessionId",loginResponse.getSessionId()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }
}
