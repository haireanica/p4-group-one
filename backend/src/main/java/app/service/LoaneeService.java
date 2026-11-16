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

    public Loanee save(Loanee loanee) {
        return repo.save(loanee);
    }

    public Loanee updateLoanee(Integer id, Loanee updatedLoanee) {
        Loanee existingLoanee = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Participant not found with ID: " + id));
        existingLoanee.setFirstName(updatedLoanee.getFirstName());
        existingLoanee.setLastName(updatedLoanee.getLastName());
        existingLoanee.setPhoneNumber(updatedLoanee.getPhoneNumber());
        existingLoanee.setAddressLine1(updatedLoanee.getAddressLine1());
        existingLoanee.setAddressLine2(updatedLoanee.getAddressLine2());
        existingLoanee.setCity(updatedLoanee.getCity());
        existingLoanee.setState(updatedLoanee.getState());
        existingLoanee.setPostalCode(updatedLoanee.getPostalCode());
        return repo.save(existingLoanee);
    }
}