package com.example.assettracker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class PasswordHashingTest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepo;

    @BeforeEach
    void createRoles() {
        roleRepo.save(new Role("STAFF"));
        roleRepo.save(new Role("ADMIN"));
    }

    @Test
    void registerStoresHashNotPlainText() {
        controller.register(new UserController.RegisterRequest(100L, "hash-test", "MySecret123", "STAFF"));

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
