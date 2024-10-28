package com.ensa.EmployeManagment.repository;

import com.ensa.EmployeManagment.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    boolean existsByEmail(String email);
    Optional<Employe> findByEmail(String email);
}