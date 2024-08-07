package dev.prabhjotaulakh.fuel.api.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("User '" + username + "' already exists !");
    }
}
