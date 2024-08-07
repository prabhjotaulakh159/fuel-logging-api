package dev.prabhjotaulakh.fuel.api.exceptions;

public class DuplicateCredentialsException extends RuntimeException {
    public DuplicateCredentialsException() {
        super("Username and password cannot be the same !");
    }

    public DuplicateCredentialsException(String message) {
        super(message);
    }
}
