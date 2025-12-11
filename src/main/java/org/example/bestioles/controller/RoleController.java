package org.example.bestioles.controller;

import jakarta.validation.Valid;
import org.example.bestioles.model.Role;
import org.example.bestioles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@Validated
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Role role) {
        if (role.getId() != null) {
            return ResponseEntity.badRequest().body("Le rôle ne doit pas avoir d'id pour la création.");
        }
        return ResponseEntity.ok(roleService.create(role));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Role role) {
        if (role.getId() == null) {
            return ResponseEntity.badRequest().body("Le rôle doit avoir un id pour la mise à jour.");
        }
        return ResponseEntity.ok(roleService.update(role));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return roleService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

