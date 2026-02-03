package br.com.techthordev.employee_management_system.exception;

/**
 * Thrown wen a requested resource does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
