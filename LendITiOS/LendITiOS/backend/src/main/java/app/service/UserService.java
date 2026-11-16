package app.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import app.domain.Role;
import app.domain.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository users;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.users = users;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @Transactional
    public User register(String username, String password, String roleName) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }
        if (username.length() > 155) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username must be at most 155 characters");
        }
        if (roleName == null || roleName.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role cannot be empty");
        }
        if (users.findByUserName(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Role"));

        String hash = encoder.encode(password);

        // Use the 3-argument constructor to bypass protected access
        User newUser = new User(username, hash, role);

        return users.save(newUser);
    }

    public User authenticate(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }
        return users.findByUserName(username)
                .filter(u -> encoder.matches(password, u.getPasswordHash()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
    }
}