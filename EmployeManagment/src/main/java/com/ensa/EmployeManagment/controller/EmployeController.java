package com.ensa.EmployeManagment.controller;

import com.ensa.EmployeManagment.dto.EmployeDTO;
import com.ensa.EmployeManagment.exception.EmailAlreadyInUseException;
import com.ensa.EmployeManagment.exception.EmployeNotFoundException;
import com.ensa.EmployeManagment.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmploye(@RequestBody EmployeDTO employeDTO) {
        try {
            EmployeDTO created = employeService.createEmploye(employeDTO);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "Employe is well added / Employé bien ajouté",
                            "employe", created
                    ));
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmploye(@PathVariable Long id, @RequestBody EmployeDTO employeDTO) {
        try {
            EmployeDTO updated = employeService.updateEmploye(id, employeDTO);
            return ResponseEntity.ok(updated);
        } catch (EmployeNotFoundException | EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        try {
            employeService.deleteEmploye(id);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Employe deleted successfully"));
        } catch (EmployeNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmploye(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeService.getEmployeById(id));
        } catch (EmployeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeDTO>> getAllEmployes() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }
}