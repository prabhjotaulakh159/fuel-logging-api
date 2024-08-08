package dev.prabhjotaulakh.fuel.api.exceptions;

public class ResourceNotOwnedByUserException extends RuntimeException {
    public ResourceNotOwnedByUserException() {
        super("You are permitted to access this resource");
    }
}
