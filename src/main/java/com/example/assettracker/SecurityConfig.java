package com.example.assettracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// PROTOTYPE ONLY: every endpoint is open so the demo works without logging in.
// Before this is real, replace permitAll() with login backed by the
// credentials/role/permission tables (e.g. staff-only access to /api/**).
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));  // lets the H2 console render
        return http.build();
    }

    // BCrypt salts every password and is deliberately slow, so a leaked
    // credentials table can't be reversed or brute-forced cheaply.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
