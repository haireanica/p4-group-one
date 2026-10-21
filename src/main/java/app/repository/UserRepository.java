package app.repository;

import java.util.Optional;

import app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data builds the query from the method name: WHERE username = ?
    Optional<User> findByUserName(String userName);
}
