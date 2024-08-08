package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.exceptions.DuplicateCredentialsException;
import dev.prabhjotaulakh.fuel.api.exceptions.UserAlreadyExistsException;
import dev.prabhjotaulakh.fuel.api.models.User;
import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, 
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void addUserToDatabase(String username, String password) {
        if (username.equals(password)) throw new DuplicateCredentialsException();
        if (userRepository.existsByUsername(username)) throw new UserAlreadyExistsException(username);
        
        var user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setSheets(new ArrayList<>());
        
        userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        var maybeGoodCreds = new UsernamePasswordAuthenticationToken(username, password);
        var auth = authenticationManager.authenticate(maybeGoodCreds);

        return userRepository.findByUsername(auth.getName()).get();
    }

    @Transactional
    public void deleteUser(String username, String password) {
        var authenticatedUser = authenticateUser(username, password);
        userRepository.deleteById(authenticatedUser.getUserId());
    }

    @Transactional
    public void changePassword(String username, String password, String newPassword) {
        if (username.equals(newPassword) || password.equals(newPassword)) {
            throw new DuplicateCredentialsException("New password must be different from old password and username");
        }
        var authenticatedUser = authenticateUser(username, password); // authenticate with old password
        authenticatedUser.setPassword(bCryptPasswordEncoder.encode(newPassword)); // encrypt new password
    }
}
