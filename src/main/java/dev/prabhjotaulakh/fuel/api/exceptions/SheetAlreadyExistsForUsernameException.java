package dev.prabhjotaulakh.fuel.api.exceptions;

public class SheetAlreadyExistsForUsernameException extends RuntimeException {
    public SheetAlreadyExistsForUsernameException(String sheetName, String username) {
        super("Sheet '" + sheetName + "' already exists for user '" + username + "'");
    }
}
