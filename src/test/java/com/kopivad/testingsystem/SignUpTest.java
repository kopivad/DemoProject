package com.kopivad.testingsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class SignUpTest {
    private static final String SIGN_UP_URL = "/signup";
    private static final String SIGN_UP_TEST_NICKNAME = "test";
    private static final String SIGN_UP_TEST_PASSWORD = "test";
    private static final String SIGN_UP_TEST_EMAIL = "test@test.com";
    private static final String SIGN_UP_TEST_EXIST_EMAIL = "admin@gmail.com";
    private static final String LOGIN_URL = "/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void availableSignUpPage() throws Exception {
        this.mockMvc.perform(get(SIGN_UP_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Registration")));
    }

    @Test
    public void emptySignUpFormDataMessageTest() throws Exception {
        this.mockMvc.perform(post(SIGN_UP_URL).with(csrf())
                .param("nickname", " ")
                .param("password", " "))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nickname can not be empty")))
                .andExpect(content().string(containsString("Password can not be empty")));
    }

    @Test
    @Sql(value = {"/drop-all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void rightSignupDataTest() throws Exception{
        this.mockMvc.perform(post(SIGN_UP_URL).with(csrf())
                .param("nickname", SIGN_UP_TEST_NICKNAME)
                .param("password", SIGN_UP_TEST_PASSWORD)
                .param("email", SIGN_UP_TEST_EMAIL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_URL));
    }

    @Test
    public void emailExistMessageTest() throws Exception {
        this.mockMvc.perform(post(SIGN_UP_URL).with(csrf())
                .param("email", SIGN_UP_TEST_EXIST_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("User with email %s exist!", SIGN_UP_TEST_EXIST_EMAIL))));
    }
}
