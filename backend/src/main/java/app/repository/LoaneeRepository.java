package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Loanee;

/**
 * Provides database operations for {@link Loanee} records.
 */
public interface LoaneeRepository extends JpaRepository<Loanee, Integer> {

}
