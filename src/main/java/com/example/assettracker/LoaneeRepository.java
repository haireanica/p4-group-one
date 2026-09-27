package com.example.assettracker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaneeRepository extends JpaRepository<Loanee, Long> {
}
