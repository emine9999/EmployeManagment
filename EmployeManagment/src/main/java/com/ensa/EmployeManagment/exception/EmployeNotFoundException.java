package com.ensa.EmployeManagment.exception;

public class EmployeNotFoundException extends RuntimeException {
    public EmployeNotFoundException() {
        super("Employe not found / Employé introuvable");
    }
}


