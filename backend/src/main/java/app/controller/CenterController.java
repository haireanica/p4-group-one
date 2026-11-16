package app.controller;

import app.domain.Center;
import app.repository.CenterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    private final CenterRepository centerRepository;

    public CenterController(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @GetMapping
    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }
}