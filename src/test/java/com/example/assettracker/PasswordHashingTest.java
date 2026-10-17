package com.example.assettracker;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordHashingTest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void registerStoresHashNotPlainText() {
        controller.register(new UserController.RegisterRequest(100L, "hash-test", "MySecret123"));

        String stored = repo.findById(100L).orElseThrow().getPasswordHash();
        System.out.println("Stored in DB: " + stored);

        assertThat(stored).isNotEqualTo("MySecret123");
        assertThat(stored).startsWith("$2a$");                     // BCrypt format
        assertThat(encoder.matches("MySecret123", stored)).isTrue();
        assertThat(encoder.matches("wrong", stored)).isFalse();
    }

    @Test
    void samePasswordGivesDifferentHashes() {
        // A random salt per hash means two users with the same password
        // still get different hashes.
        assertThat(encoder.encode("MySecret123")).isNotEqualTo(encoder.encode("MySecret123"));
    }
}
