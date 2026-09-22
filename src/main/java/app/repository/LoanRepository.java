package app.repository;

import app.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;




public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
