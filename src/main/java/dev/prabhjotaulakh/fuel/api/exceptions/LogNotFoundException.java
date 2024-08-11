package dev.prabhjotaulakh.fuel.api.exceptions;

public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException() {
        super("Unable to locate log");
    }
}
