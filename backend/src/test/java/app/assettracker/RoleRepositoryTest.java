package app.assettracker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import app.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Role;

@SpringBootTest
@Transactional
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    void roleCreation() {
        // Use a unique name to prevent duplicate entry collisions with pre-seeded roles
        Role testRole = new Role("TEST_ADMIN");
        Role saved = repo.save(testRole);

        Optional<Role> result = repo.findById(saved.getId());

        System.out.print(result.toString());
        assertThat(result).isPresent();
    }
}