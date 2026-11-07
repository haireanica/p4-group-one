package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.domain.Loanee;
import app.repository.LoaneeRepository;

@Service
public class LoaneeService {

    private final LoaneeRepository repo;

    public LoaneeService(LoaneeRepository repo) {
        this.repo = repo;
    }

    public List<Loanee> findAll() {
        return repo.findAll();
    }
}