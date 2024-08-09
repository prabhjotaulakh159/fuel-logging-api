package dev.prabhjotaulakh.fuel.api.exceptions;

public class InvalidCountryException extends RuntimeException {
    public InvalidCountryException(String country) {
        super("Country '" + country + "' is not valid");
    }
}
