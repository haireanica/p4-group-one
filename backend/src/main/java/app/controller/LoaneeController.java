package app.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.domain.Loanee;
import app.service.LoaneeService;

@RestController
@RequestMapping("/api/loanees")
public class LoaneeController {

    private final LoaneeService service;

    public LoaneeController(LoaneeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Loanee> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createLoanee(@RequestBody Loanee loanee) {
        try {
            Loanee savedLoanee = service.save(loanee);
            return ResponseEntity.ok(savedLoanee);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the exact validation or mapping error in your IDE console
            return ResponseEntity.badRequest().body("Failed to register loanee: " + e.getMessage());
        }
    }
}