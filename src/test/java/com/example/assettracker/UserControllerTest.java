package com.example.assettracker;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;

    
@SpringBootTest
@AutoConfigureMockMvc   // gives you a fake HTTP client that goes through Spring's full request pipeline
@Transactional          // rolls back the DB after each test, so tests don't leak users into each other
class UserControllerTest {

      @Autowired
      private MockMvc mvc;

      @Test
      void registerValidUserReturns201() throws Exception {
          mvc.perform(post("/api/users/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("""
                      {"userId":1,"username":"alice","password":"Secret123"}
                      """))
             .andExpect(status().isCreated())
             .andExpect(jsonPath("$.username").value("alice"))
             .andExpect(jsonPath("$.password").doesNotExist());   // the hash must never leak
      }

      @Test
      void registerBlankUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userId":1,"username":"","password":"Secret123"}
                """))
        .andExpect(status().isBadRequest());
      }

      @Test
      void registerMissingUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userId":1,"password":"Secret123"}
                """))
        .andExpect(status().isBadRequest());
      }

      @Test
      void registerMissingUserId400() throws Exception {
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"username":"raph","password":"Secret123"}
                    """))
        .andExpect(status().isBadRequest());
      }

    @Test
    void registerBlankPassword400() throws Exception {
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"userId":"100","username":"raph","password":" "}
                    """))
        .andExpect(status().isBadRequest());
      }

    @Test
    void registerDuplicateUserId409() throws Exception {
          mvc.perform(post("/api/users/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("""
                      {"userId":1,"username":"alice","password":"Secret123"}
                      """))
             .andExpect(status().isCreated());
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"userId":1,"username":"raph","password":"Secret123"}
                    """))
        .andExpect(status().isConflict());
    }

        @Test
    void registerDuplicateUsername409() throws Exception {
          mvc.perform(post("/api/users/register")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("""
                      {"userId":1,"username":"raph","password":"Secret123"}
                      """))
             .andExpect(status().isCreated());
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"userId":2,"username":"raph","password":"Secret123"}
                    """))
        .andExpect(status().isConflict());
    }


    @Test
    void loginValidCredentialsReturns200() throws Exception{
            mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"userId":1,"username":"raph","password":"Secret123"}
                    """))
            .andExpect(status().isCreated());
        
            mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"raph","password":"Secret123"}
                        """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("raph"))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void loginWrongPasswordReturns401() throws Exception{
            mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"userId":1,"username":"raph","password":"Secret123"}
                    """))
            .andExpect(status().isCreated());
        
            mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"raph","password":"Secret"}
                        """))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void loginUnknownUserReturns401() throws Exception{
            mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"raph","password":"Secret123"}
                        """))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void loginMissingPasswordReturns400() throws Exception{
            mvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"userId":1,"username":"raph","password":"Secret123"}
                    """))
            .andExpect(status().isCreated());

            mvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"username":"raph"}
                        """))
            .andExpect(status().isBadRequest());
    }

}


