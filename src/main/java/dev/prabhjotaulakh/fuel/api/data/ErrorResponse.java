package dev.prabhjotaulakh.fuel.api.data;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private Integer status;
    private LocalDateTime localDateTime;
    
    public ErrorResponse() {
    }

    public ErrorResponse(String message, Integer status, LocalDateTime localDateTime) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
