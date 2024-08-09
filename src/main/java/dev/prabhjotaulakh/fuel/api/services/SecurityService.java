package dev.prabhjotaulakh.fuel.api.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.models.User;
import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;

@Service
public class SecurityService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static String getCurrentlyLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getAuthenticatedUser() {
        var username = SecurityService.getCurrentlyLoggedInUsername();
        var maybeAuthenticatedUser = userRepository.findByUsername(username);
        if (maybeAuthenticatedUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return maybeAuthenticatedUser.get();
    }
}
