package com.example.assettracker;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest 
public class RoleRepositoryTest {
    
    @Autowired 
    private RoleRepository repo;

    @Test
    void roleCreation(){
        Role testRole = new Role("ADMIN");
        Role saved = repo.save(testRole);

        Optional<Role> result = repo.findById(saved.getId());

        System.out.print(result.toString());
        assertThat(result).isPresent();

    }
}
