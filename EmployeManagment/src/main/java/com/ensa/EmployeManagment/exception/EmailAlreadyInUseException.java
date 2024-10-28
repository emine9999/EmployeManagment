package com.ensa.EmployeManagment.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("Email already in use / Email déjà utilisé");
    }
}
