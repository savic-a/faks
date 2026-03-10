package com.example.cloudcomputing.springdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bread")
public class BreadController {

    @Autowired
    private BreadRepository breadRepository;

    @GetMapping
    public ResponseEntity<List<Bread>> getBreads() {
        return ResponseEntity.ok(breadRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bread> getBread(@PathVariable Long id) {
        Bread bread = breadRepository.findById(id).orElse(null);
        if (bread == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bread);
    }

    @PostMapping
    public ResponseEntity<Bread> addBread(@RequestBody Bread bread) {
        bread = breadRepository.save(bread);
        return ResponseEntity.ok(bread);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bread> updateBread(@PathVariable Long id, @RequestBody Bread bread) {
        Bread storedBread = breadRepository.findById(id).orElse(null);
        if (storedBread == null) {
            return ResponseEntity.notFound().build();
        }
        bread.setId(storedBread.getId());
        breadRepository.save(bread);
        return ResponseEntity.ok(bread);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBread(@PathVariable Long id) {
        Bread bread = breadRepository.findById(id).orElse(null);
        if (bread == null) {
            return ResponseEntity.notFound().build();
        }
        breadRepository.delete(bread);
        return ResponseEntity.noContent().build();
    }
}
