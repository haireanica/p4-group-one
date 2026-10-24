package com.example.assettracker;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MockMvc;

// Invalid inputs for checkout and return. Asset and loan ids come from the
// V3 demo data: asset "1003" is AVAILABLE, "1001" is ON_LOAN, loan 1 is ACTIVE,
// participant 5001 exists.
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = "raph", roles = "STAFF") // checkout and return are everyday STAFF tasks
public class LoanControllerTest {

    @Autowired
    private MockMvc mvc;

    // --- checkout: 400, the request itself is incomplete or unreadable ---

    @Test
    void checkoutMissingAssetIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"participantId": 1, "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isBadRequest());
    }

    // assetId is text now, so "" and "  " must be rejected like a missing one.
    @Test
    void checkoutBlankAssetIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": " ", "participantId": 5001, "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMissingParticipantIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMissingReasonReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "participantId": 5001, "days": 14}"""))
        .andExpect(status().isBadRequest());
    }

    // "PARTY" is not a LoanReason, so Spring can't turn the JSON into the request record.
    @Test
    void checkoutUnknownReasonReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "participantId": 5001, "reason": "PARTY", "days": 14}"""))
        .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutMalformedJsonReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", """))
        .andExpect(status().isBadRequest());
    }

    // --- checkout: 404 and 409, the request is complete but the data says no ---

    @Test
    void checkoutUnknownAssetReturns404() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "9999", "participantId": 5001, "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isNotFound());
    }

    @Test
    void checkoutAssetAlreadyOnLoanReturns409() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1001", "participantId": 5001, "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isConflict());
    }

    // Loanees need an address in the team schema, so checkout no longer
    // creates them on the fly.
    @Test
    void checkoutUnknownParticipantReturns404() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "participantId": 9999, "reason": "EDUCATION", "days": 14}"""))
        .andExpect(status().isNotFound());
    }

    @Test
    void checkoutWithNegativeDaysReturns400() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "participantId": 5001, "reason": "EDUCATION", "days": -5}"""))
        .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutWithoutDaysReturns201() throws Exception {
        mvc.perform(post("/api/loans/checkout")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {"assetId": "1003", "participantId": 5001, "reason": "EDUCATION"}"""))
        .andExpect(status().isCreated());
    }

    // --- return ---

    @Test
    void returnUnknownLoanReturns404() throws Exception {
        mvc.perform(post("/api/loans/99999/return"))
            .andExpect(status().isNotFound());
    }

    @Test
    void returnLoanTwiceReturns409() throws Exception {
        mvc.perform(post("/api/loans/1/return"))
            .andExpect(status().isOk());

        mvc.perform(post("/api/loans/1/return"))
            .andExpect(status().isConflict());
    }

    @Test
    void returnNonNumericLoanIdReturns400() throws Exception {
        mvc.perform(post("/api/loans/abc/return"))
            .andExpect(status().isBadRequest());
    }
}
