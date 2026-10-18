package com.example.assettracker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc   // gives you a fake HTTP client that goes through Spring's full request pipeline
@Transactional          // rolls back the DB after each test, so tests don't leak users into each other
public class SecurityTest {
    
    @Autowired 
    private MockMvc mvc; 


    @Test
    void assetsWithoutCredentialsReturns401() throws Exception {
        mvc.perform(get("/api/assets"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "raph", roles = "USER")
    void assetsWithCredentialReturns200() throws Exception {
        mvc.perform(get("/api/assets"))
            .andExpect(status().isOk());
    }

    @Test
    void assetsWithRegisteredUserReturns200() throws Exception {
            mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"userId":1,"username":"raph","password":"Secret123"}
                    """))
                .andExpect(status().isCreated());
            mvc.perform(get("/api/assets")
                    .with(httpBasic("raph", "Secret123")))
                    .andExpect(status().isOk());
    }



}
