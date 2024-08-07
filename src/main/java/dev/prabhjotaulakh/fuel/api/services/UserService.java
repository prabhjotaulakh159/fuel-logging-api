package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.exceptions.DuplicateCredentialsException;
import dev.prabhjotaulakh.fuel.api.exceptions.UserAlreadyExistsException;
import dev.prabhjotaulakh.fuel.api.models.User;
import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public int addUserToDatabase(String username, String password) {
        if (username.equals(password)) throw new DuplicateCredentialsException();
        if (userRepository.existsByUsername(username)) throw new UserAlreadyExistsException(username);
        
        var user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setSheets(new ArrayList<>());
        
        userRepository.save(user);
        
        return user.getUserId();
    }
}
