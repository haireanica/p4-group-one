package com.example.assettracker;

import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/loanees")
public class LoaneeController {
    
    private final LoaneeRepository repo;

    public LoaneeController(LoaneeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Loanee> getAll() {
        return repo.findAll();
    }


}
