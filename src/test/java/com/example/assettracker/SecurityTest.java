package com.example.assettracker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc   // gives you a fake HTTP client that goes through Spring's full request pipeline
@Transactional          // rolls back the DB after each test, so tests don't leak users into each other
public class SecurityTest {
    
    @Autowired 
    private MockMvc mvc; 

    @Autowired 
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository users;

    @Autowired 
    private PasswordEncoder encoder;

    @BeforeEach
      void createRoles() {
        roleRepo.save(new Role("STAFF"));
        roleRepo.save(new Role("ADMIN"));
    }

    @Test
    void assetsWithoutCredentialReturns401() throws Exception {
        mvc.perform(get("/api/assets"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "raph", roles = "STAFF")
    void assetsWithCredentialReturns200() throws Exception {
        mvc.perform(get("/api/assets"))
            .andExpect(status().isOk());
    }

    @Test
    void assetsWithRegisteredUserReturns200() throws Exception {
            mvc.perform(post("/api/users/register")
                .with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"userId": 2,"username": "raph","password": "Secret123", "role": "STAFF"}
                    """))
                .andExpect(status().isCreated());
            mvc.perform(get("/api/assets")
                    .with(httpBasic("raph", "Secret123")))
                    .andExpect(status().isOk());
    }

    @Test
    void assetsWithWrongPasswordReturns401() throws Exception {
        mvc.perform(post("/api/users/register")
            .with(user("admin").roles("ADMIN"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"userId": 1,"username":"raph","password":"Secret123", "role": "STAFF"}
                    """))
            .andExpect(status().isCreated());

        mvc.perform(get("/api/assets")
            .with(httpBasic("raph", "Secret")))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void registrationByUserNotAuthorizedReturns401() throws Exception {
        mvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userId":1, "username":"raph","password":"secret123"}
            """))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void registerAsStaffReturns403() throws Exception {
        mvc.perform(post("/api/users/register")
            .with(user("staff").roles("STAFF"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"userId":1, "username":"raph","password":"secret123"}
            """))
            .andExpect(status().isForbidden());
    }

    @Test
    void staffAccessToLoaneeReturns403() throws Exception {
        mvc.perform(get("/api/loanees")
            .with(user("staff").roles("STAFF")))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminAccessToLoaneeReturns200() throws Exception {
        mvc.perform(get("/api/loanees")
            .with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk());
    }

    @Test
    void staffCannotAddDeviceReturns403() throws Exception {
        mvc.perform(post("/api/assets")
            .with(user("staff").roles("STAFF"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"assetId": 1, "name": "TAG-1", "assetStatus": "AVAILABLE"}
                    """))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminCanAddDeviceReturns200() throws Exception {
        mvc.perform(post("/api/assets")
        .with(user("admin").roles("ADMIN"))
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {"assetId": 1, "name": "TAG-1", "assetStatus": "AVAILABLE"}
                """))
        .andExpect(status().isOk());
    }


    @Test
    void noRoleLoginReturns403() throws Exception {
        users.save(new User(1L, "raph", encoder.encode("Secret123"), null));

            mvc.perform(get("/api/assets")
                    .with(httpBasic("raph", "Secret123")))
                    .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "raph", roles = "STAFF")
    void staffCanAccessLoansReturns200() throws Exception {
        mvc.perform(get("/api/loans")
            .with(httpBasic("raph", "Secret123")))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "raph", roles = "STAFF")
    void staffCanCheckoutAssetsReturns201() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"assetId": 1003, "participantId": 5001, "reason": "EDUCATION", "days": 14} """))
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "raph", roles = "STAFF")
    void staffCanReturnLoansReturns200() throws Exception {
        mvc.perform(post("/api/loans/1/return"))
            .andExpect(status().isOk());
    }
}
