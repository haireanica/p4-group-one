package app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import app.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}