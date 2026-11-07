package app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import app.domain.User;
import app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public record RegisterRequest(String username, String password, String role) {}
    public record LoginRequest(String username, String password) {}
    public record UserResponse(Integer userId, String username) {}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest req) {
        User saved = userService.register(req.username(), req.password(), req.role());
        return new UserResponse(saved.getId(), saved.getUserName());
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest req) {
        User user = userService.authenticate(req.username(), req.password());
        return new UserResponse(user.getId(), user.getUserName());
    }
}