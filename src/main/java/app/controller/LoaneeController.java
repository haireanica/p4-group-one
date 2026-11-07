package app.controller;

import java.util.List;
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
}