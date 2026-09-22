package app.repository;

import app.domain.Loanee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaneeRepository extends JpaRepository<Loanee, Integer> {
}
