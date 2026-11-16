package app.repository;

import app.domain.Center;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides database operations for {@link Center} records.
 */
public interface CenterRepository extends JpaRepository<Center, Integer> {

}
