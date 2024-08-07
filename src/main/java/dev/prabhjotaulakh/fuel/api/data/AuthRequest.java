package dev.prabhjotaulakh.fuel.api.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRequest {
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[A-Za-z0-9_]{4,}$", message = "Username must be atleast 4 characters, and have only letters, numbers and underscores")
    private String username;
    
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$", message = "Password must be atleast 8 characters, with atleast one uppercase, one lowercase and one number")
    private String password;
    
    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
