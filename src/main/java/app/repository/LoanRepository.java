package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Loan;


public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
