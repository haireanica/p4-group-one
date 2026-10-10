package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Loan;

/**
 * Provides database operations for {@link Loan} records.
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
