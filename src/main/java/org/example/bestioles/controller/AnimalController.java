package org.example.bestioles.controller;

import jakarta.validation.Valid;
import org.example.bestioles.model.Animal;
import org.example.bestioles.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animal")
@Validated
public class AnimalController {
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Animal animal) {
        if (animal.getId() != null) {
            return ResponseEntity.badRequest().body("L'animal ne doit pas avoir d'id pour la création.");
        }
        return ResponseEntity.ok(animalService.create(animal));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Animal animal) {
        if (animal.getId() == null) {
            return ResponseEntity.badRequest().body("L'animal doit avoir un id pour la mise à jour.");
        }
        return ResponseEntity.ok(animalService.update(animal));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        animalService.delete(id);
    }

    @GetMapping
    public List<Animal> findAll() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return animalService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
