package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Center;

/**
 * Provides database operations for {@link Center} records.
 */
public interface CenterRepository extends JpaRepository<Center, Integer> {

}
