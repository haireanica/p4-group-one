package app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import app.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Spring Data builds query from name: WHERE username = ?
    Optional<User> findByUserName(String userName);
}