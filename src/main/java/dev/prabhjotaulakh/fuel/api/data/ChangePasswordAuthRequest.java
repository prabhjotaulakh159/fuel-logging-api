package dev.prabhjotaulakh.fuel.api.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordAuthRequest extends AuthRequest {
    @NotBlank(message = "New password cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$", message = "New password must be atleast 8 characters, with atleast one uppercase, one lowercase and one number")
    private String newPassword;

    public ChangePasswordAuthRequest() {
    }

    public ChangePasswordAuthRequest(String username, String password, String newPassword) {
        super(username, password);
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
