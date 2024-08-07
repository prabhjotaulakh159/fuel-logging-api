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
}
