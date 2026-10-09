package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.domain.Loanee;

public interface LoaneeRepository extends JpaRepository<Loanee, Integer> {

}
