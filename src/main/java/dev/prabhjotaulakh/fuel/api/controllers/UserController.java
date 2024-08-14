package dev.prabhjotaulakh.fuel.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.prabhjotaulakh.fuel.api.data.AuthRequest;
import dev.prabhjotaulakh.fuel.api.data.ChangePasswordAuthRequest;
import dev.prabhjotaulakh.fuel.api.data.TokenResponse;
import dev.prabhjotaulakh.fuel.api.services.TokenService;
import dev.prabhjotaulakh.fuel.api.services.UserService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "localhost")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid AuthRequest authRequest) {
        userService.addUserToDatabase(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody @Valid AuthRequest authRequest) {
        var user = userService.authenticateUser(authRequest.getUsername(), authRequest.getPassword());
        var tokenStr = tokenService.generateToken(user); 
        return ResponseEntity.ok().body(new TokenResponse(tokenStr));
    }

    @DeleteMapping("/private/user/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody @Valid AuthRequest authRequest) {
        userService.deleteUser(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/private/user/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid ChangePasswordAuthRequest changePasswordAuthRequest) {
        userService.changePassword(changePasswordAuthRequest.getUsername(), changePasswordAuthRequest.getPassword(), changePasswordAuthRequest.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
