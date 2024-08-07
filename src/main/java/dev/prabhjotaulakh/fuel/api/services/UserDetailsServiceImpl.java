package dev.prabhjotaulakh.fuel.api.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;

/**
 * Used by an authentication bean to perform authentication against a database.
 * See config/SecurityConfig.java
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var maybeUser = userRepository.findByUsername(username);
        
        if (maybeUser.isEmpty()) throw new UsernameNotFoundException(username + " not found !");

        var user = maybeUser.get();

        // note this is not the User from our models
        // This User.builder() is called on the spring security User type
        var userDetails = User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .build();
        
        return userDetails;
    }    
}
