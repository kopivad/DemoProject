package com.kopivad.testingsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LogInTest {


    private static final String LOGIN_PAGE_URL = "/login";
    private static final String INDEX_PAGE_URL = "/index";
    private static final String REDIRECTED_URL = "/login?error";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void availableLogInPage() throws Exception {
        this.mockMvc.perform(get(LOGIN_PAGE_URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sign in")));
    }

    @Test
    @WithUserDetails(value = "user@gmail.com")
    public void successLogin() throws Exception {
        this.mockMvc.perform(get(INDEX_PAGE_URL))
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[1]/div/nav[2]/div/form/a").string("User"));
    }

    @Test
    public void emptyLoginFormDataTest() throws Exception {
        this.mockMvc.perform(post(LOGIN_PAGE_URL).with(csrf())
                .param("username", " ")
                .param("password", " "))
                .andExpect(status().is3xxRedirection())
                .andExpect(content().string(containsString("Invalid username and password.")));
    }
}
