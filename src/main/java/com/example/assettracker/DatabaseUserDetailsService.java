package com.example.assettracker;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public DatabaseUserDetailsService(UserRepository users) {
        this.users = users;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        
        Optional<User> userBox = users.findByUserName(username);

        if (userBox.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = userBox.get();

        
        if (user.getRole() == null || user.getRole().getName().isBlank()) {
            return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPasswordHash())
                .roles()
                .build();
        }
            return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPasswordHash())
                .roles(user.getRole().getName())
                .build();
    }
}
