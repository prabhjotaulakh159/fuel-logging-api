package dev.prabhjotaulakh.fuel.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.prabhjotaulakh.fuel.api.data.AuthRequest;
import dev.prabhjotaulakh.fuel.api.services.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid AuthRequest authRequest) {
        var userId = userService.addUserToDatabase(authRequest.getUsername(), authRequest.getPassword());
        
        return ResponseEntity.ok().body("User ID: " + userId);
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid AuthRequest authRequest) {
        var username = userService.authenticateUser(authRequest.getUsername(), authRequest.getPassword());

        return ResponseEntity.ok().body(username);
    }
}
