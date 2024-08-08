package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.data.SheetResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.ResourceNotOwnedByUserException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetAlreadyExistsForUsernameException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetNotFoundException;
import dev.prabhjotaulakh.fuel.api.models.Sheet;
import dev.prabhjotaulakh.fuel.api.repositories.SheetRepository;
import dev.prabhjotaulakh.fuel.api.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class SheetService {
    private final SheetRepository sheetRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public SheetService(SheetRepository sheetRepository, UserRepository userRepository,
            ModelMapper modelMapper) {
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public SheetResponse addSheet(String sheetName) {
        var maybeAuthenticatedUser = userRepository.findByUsername(SecurityService.getCurrentlyLoggedInUsername());

        if (maybeAuthenticatedUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
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

    public SheetResponse getSheetById(Integer sheetId) {
        var user = userRepository.findByUsername(SecurityService.getCurrentlyLoggedInUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        
        var sheet = sheetRepository.findById(sheetId);
        if (sheet.isEmpty()) {
            throw new SheetNotFoundException();
        }

        // basically the sheet should belong to the logged in user 
        // in order to access it
        if (sheet.get().getUser().getUserId() != user.get().getUserId()) {
            throw new ResourceNotOwnedByUserException();
        }

        var sheetResponse = new SheetResponse();
        sheetResponse.setSheetId(sheet.get().getSheetId());
        sheetResponse.setSheetName(sheet.get().getSheetName());
        sheetResponse.setLogs(sheet.get().getLogs());

        return sheetResponse;
    }

    public List<SheetResponse> getAllSheets() {
        var user = userRepository.findByUsername(SecurityService.getCurrentlyLoggedInUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var sheets = sheetRepository.getAllSheetsByUserId(user.get().getUserId());
        var sheetResponseList = sheets.stream()
            .map(sheet -> modelMapper.map(sheet, SheetResponse.class))
            .collect(Collectors.toList());

        return sheetResponseList;
    }
}
