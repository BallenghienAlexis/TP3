package org.example.bestioles.controller;

import org.example.bestioles.model.Person;
import org.example.bestioles.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/person")
@Validated
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Person person) {
        if (person.getId() != null) {
            return ResponseEntity.badRequest().body("La personne ne doit pas avoir d'id pour la création.");
        }
        return ResponseEntity.ok(personService.create(person));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Person person) {
        if (person.getId() == null) {
            return ResponseEntity.badRequest().body("La personne doit avoir un id pour la mise à jour.");
        }
        return ResponseEntity.ok(personService.update(person));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/page")
    public Page<Person> findAllPage(Pageable pageable) {
        return personService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

