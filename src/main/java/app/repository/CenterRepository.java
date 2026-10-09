package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Center;

/**
 * The CenterRepository interface is a Spring Data Jpa repository
 * that allows for the Center entity to be persisted in the database
 * and retrieved from the database.
 */
public interface CenterRepository extends JpaRepository<Center, Integer> {

}
