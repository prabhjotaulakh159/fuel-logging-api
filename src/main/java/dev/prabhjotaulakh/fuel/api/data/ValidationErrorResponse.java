package dev.prabhjotaulakh.fuel.api.data;

import java.time.LocalDateTime;
import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
    private List<String> validationErrors;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(String message, Integer status, LocalDateTime localDateTime, List<String> validationErrors) {
        super(message, status, localDateTime);
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
