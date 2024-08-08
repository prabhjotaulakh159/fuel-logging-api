package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.data.SheetResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetAlreadyExistsForUsernameException;
import dev.prabhjotaulakh.fuel.api.models.Sheet;
import dev.prabhjotaulakh.fuel.api.repositories.SheetRepository;
import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;

@Service
public class SheetService {
    private final SheetRepository sheetRepository;
    private final UserRepository userRepository;

    public SheetService(SheetRepository sheetRepository, UserRepository userRepository) {
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
    }

    public SheetResponse addSheet(String sheetName) {
        var authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var maybeAuthenticatedUser = userRepository.findByUsername(authenticatedUsername);

        if (maybeAuthenticatedUser.isEmpty()) {
            throw new UsernameNotFoundException("User '" + authenticatedUsername + "' not found");
        }

        var user = maybeAuthenticatedUser.get();

        if (sheetRepository.existsBySheetNameAndUsername(sheetName, user.getUsername())) {
            throw new SheetAlreadyExistsForUsernameException(sheetName, user.getUsername());
        }

        var sheet = new Sheet();
        sheet.setSheetName(sheetName);
        sheet.setLogs(new ArrayList<>());
        sheet.setUser(user);

        sheetRepository.save(sheet);

        return new SheetResponse(sheet.getSheetId(), sheet.getSheetName(), sheet.getLogs());
    } 
}
