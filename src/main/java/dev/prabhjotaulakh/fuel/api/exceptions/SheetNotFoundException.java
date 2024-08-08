package dev.prabhjotaulakh.fuel.api.exceptions;

public class SheetNotFoundException extends RuntimeException {
    public SheetNotFoundException() {
        super("Sheet not found !");
    }
}
