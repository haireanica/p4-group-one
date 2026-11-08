package app.assettracker;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;

import app.domain.Role;
import app.repository.RoleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepo;

    @BeforeEach
    void createRoles() {
        if (roleRepo.findByName("STAFF").isEmpty()) {
            roleRepo.save(new Role("STAFF"));
        }
        if (roleRepo.findByName("ADMIN").isEmpty()) {
            roleRepo.save(new Role("ADMIN"));
        }
    }

    @Test
    void registerValidUserReturns201() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                      {"username":"alice","password":"Secret123","role":"STAFF"}
                      """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void registerBlankUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"username":"","password":"Secret123","role":"STAFF"}
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerMissingUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"password":"Secret123","role":"STAFF"}
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerBlankPassword400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":" ","role":"STAFF"}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerDuplicateUsername409() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                      {"username":"raph","password":"Secret123","role":"STAFF"}
                      """))
                .andExpect(status().isCreated());
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":"ADMIN"}
                    """))
                .andExpect(status().isConflict());
    }

    @Test
    void loginValidCredentialsReturns200() throws Exception{
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":"ADMIN"}
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
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":"ADMIN"}
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
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":"STAFF"}
                    """))
                .andExpect(status().isCreated());

        mvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"username":"raph"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUnknownRoleReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":"SUPERUSER"}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerMissingRoleReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123"}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerBlankRoleReturns400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"raph","password":"Secret123","role":" "}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginMissingUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"password":"Secret123"}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginBlankUsernameReturns400() throws Exception {
        mvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":" ","password":"Secret123"}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUsernameTooLongReturns400() throws Exception {
        String longName = "a".repeat(156);
        mvc.perform(post("/api/users/register")
                        .with(user("raph").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"username":"%s","password":"Secret123","role":"STAFF"}
                    """.formatted(longName)))
                .andExpect(status().isBadRequest());
    }
}