package com.example.assettracker;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Spring Data builds the query from the method name: WHERE username = ?
    Optional<User> findByUserName(String userName);
}
