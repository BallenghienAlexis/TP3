package org.example.bestioles.controller;

import org.example.bestioles.model.Species;
import org.example.bestioles.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/species")
@Validated
public class SpeciesController {
    private final SpeciesService speciesService;

    @Autowired
    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Species species) {
        if (species.getId() != null) {
            return ResponseEntity.badRequest().body("L'espèce ne doit pas avoir d'id pour la création.");
        }
        return ResponseEntity.ok(speciesService.create(species));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Species species) {
        if (species.getId() == null) {
            return ResponseEntity.badRequest().body("L'espèce doit avoir un id pour la mise à jour.");
        }
        return ResponseEntity.ok(speciesService.update(species));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        speciesService.delete(id);
    }

    @GetMapping
    public List<Species> findAll() {
        return speciesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return speciesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
