package app.assettracker;

import app.domain.*;
import app.repository.AssetRepository;
import app.repository.LoanRepository;
import app.repository.LoaneeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private LoaneeRepository loaneeRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private EntityManager entityManager;

    // --- checkout: 400, the request itself is incomplete or unreadable ---

    @Test
    void checkoutMissingAssetIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutBlankAssetIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": " ", "participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMissingParticipantIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": "1003", "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMissingReasonReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"participantId": 5001, "reason": "Coursework", "days": 14}"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMalformedJsonReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": "1003", """))
                .andExpect(status().isBadRequest());
    }

    // --- checkout: 404 and 409, the request is complete but data says no ---

    @Test
    void checkoutUnknownAssetReturns404() throws Exception {
        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": "9999", "participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkoutAssetAlreadyOnLoanReturns409() throws Exception {
        Asset asset1001 = new Asset("1001", "ThinkPad 1", assetStatus.CHECKED_OUT, List.of(), "Laptop");
        assetRepository.save(asset1001);

        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": "1001", "participantId": 5001, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isConflict());
    }

    @Test
    void checkoutUnknownParticipantReturns404() throws Exception {
        Asset asset1003 = new Asset("1003", "ThinkPad 3", assetStatus.AVAILABLE, List.of(), "Laptop");
        assetRepository.save(asset1003);

        mvc.perform(post("/api/loans/checkout")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"assetId": "1003", "participantId": 9999, "reason": "Coursework", "loanPurpose": "Education", "days": 14}"""))
                .andExpect(status().isNotFound());
    }

    // --- return ---

    @Test
    void returnUnknownLoanReturns404() throws Exception {
        mvc.perform(post("/api/loans/99999/return")
                        .with(user("staff").roles("STAFF")))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnLoanTwiceReturns409() throws Exception {
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
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"condition": "good", "notes": ""}"""))
                .andExpect(status().isOk());

        mvc.perform(post("/api/loans/" + loanId + "/return")
                        .with(user("staff").roles("STAFF"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {"condition": "good", "notes": ""}"""))
                .andExpect(status().isConflict());
    }

    @Test
    void returnNonNumericLoanIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/abc/return")
                        .with(user("staff").roles("STAFF")))
                .andExpect(status().isBadRequest());
    }
}