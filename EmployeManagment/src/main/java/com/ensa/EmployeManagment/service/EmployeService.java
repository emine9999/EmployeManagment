package com.ensa.EmployeManagment.service;

import com.ensa.EmployeManagment.dto.EmployeDTO;
import java.util.List;

public interface EmployeService {
    EmployeDTO createEmploye(EmployeDTO employeDTO);
    EmployeDTO updateEmploye(Long id, EmployeDTO employeDTO);
    void deleteEmploye(Long id);
    EmployeDTO getEmployeById(Long id);
    List<EmployeDTO> getAllEmployes();
}