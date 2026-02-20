package br.com.techthordev.backend.exception;

/**
 * Thrown wen a requested resource does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}