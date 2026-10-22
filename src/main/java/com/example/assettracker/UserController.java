package com.example.assettracker;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository users;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public UserController(UserRepository users, PasswordEncoder encoder, RoleRepository roleRepo) {
        this.users = users;
        this.encoder = encoder;
        this.roleRepo = roleRepo;
    }

    // userId is caller-assigned, like assetId (the credentials table has no auto-increment).
    public record RegisterRequest(Long userId, String username, String password, String role) {}
    public record LoginRequest(String username, String password) {}

    // The response never includes the password or its hash.
    public record UserResponse(Long userId, String username) {}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest req) {
        if (req.userId() == null || req.username() == null ||req.username().isBlank() || req.password() == null || req.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId, username and password are required");
        }
        if (users.existsById(req.userId()) || users.findByUserName(req.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        // Only the hash is stored; the plain-text password is never saved.
        Role role = roleRepo.findByName(req.role())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Role"));
        String hash = encoder.encode(req.password());
        User saved = users.save(new User(req.userId(), req.username(), hash, role));
        return new UserResponse(saved.getId(), saved.getUserName());
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest req) {
        if (req.username() == null || req.username().isBlank() || req.password() == null || req.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }
        User user = users.findByUserName(req.username())
                .filter(u -> encoder.matches(req.password(), u.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        return new UserResponse(user.getId(), user.getUserName());
    
    }
}
