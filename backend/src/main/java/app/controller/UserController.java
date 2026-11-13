
package app.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import app.domain.User;
import app.repository.UserRepository;
import app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public UserController(
            UserService userService,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public record RegisterRequest(String username, String password, String role) {}
    public record LoginRequest(String username, String password) {}
    public record UserResponse(Integer userId, String username) {}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest req) {

        User saved = userService.register(
                req.username(),
                req.password(),
                req.role()
        );

        return new UserResponse(saved.getId(), saved.getUserName());
    }

    @PostMapping("/login")
    public UserResponse login(
            @RequestBody LoginRequest req,
            HttpServletRequest request,
            HttpServletResponse response) {

        Authentication authentication =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken.unauthenticated(
                                req.username(),
                                req.password()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                request,
                response
        );

        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow();

        return new UserResponse(user.getId(), user.getUserName());
    }
}
