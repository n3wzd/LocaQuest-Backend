package com.example.reviewsplash;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reviewsplash.model.User;
import com.example.reviewsplash.dto.LoginRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    final private ObjectMapper objectMapper = new ObjectMapper();
    static final private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @ParameterizedTest
    @Order(1)
    @CsvSource({
        "Alice, Password@123, Alice Smith, alice@example.com", 
        "Bob, Secret@Pass1, Bob Johnson, bob@example.com", 
    })
    void testCreateUser(String userId, String password, String name, String email) throws Exception {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);

        String json = objectMapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(json))
                .andReturn();
        logger.info("Response: {}", result.getResponse().getContentAsString());
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
        "Alice, Password@123, 200", 
        "Tom, asdfqwer, 401", 
        "Bob, Secret@Pass2, 401", 
        "Bob, Secret@Pass1, 200", 
    })
    void testLoginUser(String userId, String password, int expectedStatus) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(userId);
        loginRequest.setPassword(password);

        String json = objectMapper.writeValueAsString(loginRequest);
        MvcResult result = mockMvc.perform(post("/api/users/login")
                .contentType("application/json")
                .content(json))
                .andExpect(status().is(expectedStatus))
                .andReturn();
        logger.info("Response: {}", result.getResponse().getContentAsString());
    }
}
