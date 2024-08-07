package dev.prabhjotaulakh.fuel.api.exceptions;

public class JwtException extends RuntimeException {
    public JwtException() {
        super("Unable to generate bearer token for current login attempt");
    }

    public JwtException(String message) {
        super(message);
    }
}
