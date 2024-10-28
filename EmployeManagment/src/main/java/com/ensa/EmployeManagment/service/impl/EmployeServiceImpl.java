package com.ensa.EmployeManagment.service.impl;

import com.ensa.EmployeManagment.dto.EmployeDTO;
import com.ensa.EmployeManagment.exception.EmailAlreadyInUseException;
import com.ensa.EmployeManagment.exception.EmployeNotFoundException;
import com.ensa.EmployeManagment.model.Employe;
import com.ensa.EmployeManagment.repository.EmployeRepository;
import com.ensa.EmployeManagment.service.EmployeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public EmployeDTO createEmploye(EmployeDTO employeDTO) {
        if (employeRepository.existsByEmail(employeDTO.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        Employe employe = new Employe();
        BeanUtils.copyProperties(employeDTO, employe);
        Employe savedEmploye = employeRepository.save(employe);

        EmployeDTO savedDTO = new EmployeDTO();
        BeanUtils.copyProperties(savedEmploye, savedDTO);
        return savedDTO;
    }

    @Override
    public EmployeDTO updateEmploye(Long id, EmployeDTO employeDTO) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(EmployeNotFoundException::new);

        if (!employe.getEmail().equals(employeDTO.getEmail()) &&
                employeRepository.existsByEmail(employeDTO.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        BeanUtils.copyProperties(employeDTO, employe, "id");
        Employe updatedEmploye = employeRepository.save(employe);

        EmployeDTO updatedDTO = new EmployeDTO();
        BeanUtils.copyProperties(updatedEmploye, updatedDTO);
        return updatedDTO;
    }

    @Override
    public void deleteEmploye(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new EmployeNotFoundException();
        }
        employeRepository.deleteById(id);
    }

    @Override
    public EmployeDTO getEmployeById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(EmployeNotFoundException::new);

        EmployeDTO employeDTO = new EmployeDTO();
        BeanUtils.copyProperties(employe, employeDTO);
        return employeDTO;
    }

    @Override
    public List<EmployeDTO> getAllEmployes() {
        return employeRepository.findAll().stream()
                .map(employe -> {
                    EmployeDTO dto = new EmployeDTO();
                    BeanUtils.copyProperties(employe, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}