package app.assettracker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.domain.*;
import app.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private LoaneeRepository loaneeRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EntityManager entityManager;

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
    void assetsWithoutCredentialReturns401() throws Exception {
        mvc.perform(get("/api/assets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loansWithoutCredentialReturns401() throws Exception {
        mvc.perform(get("/api/loans"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void checkoutWithoutCredentialReturns401() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"assetId": "1003", "participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void returnWithoutCredentialReturns401() throws Exception {
        mvc.perform(post("/api/loans/1/return"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loaneesWithoutCredentialReturns401() throws Exception {
        mvc.perform(get("/api/loanees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void assetsWithCredentialReturns200() throws Exception {
        Role staffRole = roleRepo.findByName("STAFF").orElseGet(() -> roleRepo.save(new Role("STAFF")));
        users.save(new User(1, "raph", encoder.encode("Password123"), staffRole));

        mvc.perform(get("/api/assets")
                        .with(httpBasic("raph", "Password123")))
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
                    {"assetId": "1", "name": "TAG-1", "assetStatus": "AVAILABLE"}
                    """))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanAddDeviceReturns200() throws Exception {
        mvc.perform(post("/api/assets")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"assetId": "1", "name": "TAG-1", "assetStatus": "AVAILABLE"}
                """))
                .andExpect(status().isOk());
    }

    @Test
    void noRoleLoginReturns403() throws Exception {
        users.save(new User(1, "raph", encoder.encode("Secret123"), null));

        mvc.perform(get("/api/assets")
                        .with(httpBasic("raph", "Secret123")))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "raph", roles = "STAFF")
    void staffCanAccessLoansReturns200() throws Exception {
        Role staffRole = roleRepo.findByName("STAFF").orElseGet(() -> roleRepo.save(new Role("STAFF")));
        User raphUser = new User();
        raphUser.setUserName("raph");
        raphUser.setPasswordHash(encoder.encode("Secret123"));
        raphUser.setRole(staffRole);
        users.save(raphUser);

        mvc.perform(get("/api/loans")
                        .with(httpBasic("raph", "Secret123")))
                .andExpect(status().isOk());
    }


    @Test
    void staffCanCheckoutAssetsReturns201() throws Exception {
        Role staffRole = roleRepo.findByName("STAFF").orElseGet(() -> roleRepo.save(new Role("STAFF")));
        User staffUser = new User();
        staffUser.setUserName("checkout-staff");
        staffUser.setPasswordHash(encoder.encode("Password123"));
        staffUser.setRole(staffRole);
        users.save(staffUser);

        Asset asset1003 = new Asset("1003", "ThinkPad 3", assetStatus.AVAILABLE, List.of(), "Laptop");
        assetRepository.save(asset1003);

        entityManager.createNativeQuery(
                        "INSERT IGNORE INTO loanee (participantId, firstName, lastName, phoneNumber, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, 5001)
                .setParameter(2, "Test")
                .setParameter(3, "Resident")
                .setParameter(4, "555-0100")
                .setParameter(5, "123 Main St")
                .setParameter(6, "Ocklawaha")
                .setParameter(7, "FL")
                .setParameter(8, "32801")
                .executeUpdate();

        mvc.perform(post("/api/loans/checkout")
                        .with(httpBasic("checkout-staff", "Password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"assetId": "1003", "participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isCreated());
    }

    @Test
    void staffCanReturnLoansReturns200() throws Exception {
        Role staffRole = roleRepo.findByName("STAFF").orElseGet(() -> roleRepo.save(new Role("STAFF")));
        User staffUser = new User();
        staffUser.setUserName("return-staff");
        staffUser.setPasswordHash(encoder.encode("Password123"));
        staffUser.setRole(staffRole);
        users.save(staffUser);

        Asset asset = new Asset("1001", "ThinkPad 1", assetStatus.CHECKED_OUT, List.of(), "Laptop");
        assetRepository.save(asset);

        entityManager.createNativeQuery(
                        "INSERT IGNORE INTO loanee (participantId, firstName, lastName, phoneNumber, address_line1, city, state, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                .setParameter(1, 5001)
                .setParameter(2, "Test")
                .setParameter(3, "Resident")
                .setParameter(4, "555-0100")
                .setParameter(5, "123 Main St")
                .setParameter(6, "Ocklawaha")
                .setParameter(7, "FL")
                .setParameter(8, "32801")
                .executeUpdate();

        Loanee loanee = loaneeRepository.findById(5001).orElseThrow();

        Loan loan = new Loan(LocalDate.now(), LocalDate.now().plusDays(14), null, asset, loanStats.CHECKED_OUT, loanee, "Coursework", "Education");
        Loan savedLoan = loanRepository.save(loan);
        Integer loanId = savedLoan.getLoanId();

        mvc.perform(post("/api/loans/" + loanId + "/return")
                        .with(httpBasic("return-staff", "Password123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"condition": "good", "notes": ""}"""))
                .andExpect(status().isOk());
    }
}