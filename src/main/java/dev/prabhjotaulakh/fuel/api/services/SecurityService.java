package dev.prabhjotaulakh.fuel.api.services;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityService {
    public static String getCurrentlyLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
