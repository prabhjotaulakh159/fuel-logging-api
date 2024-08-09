package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.data.SheetRequest;
import dev.prabhjotaulakh.fuel.api.data.SheetResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.ResourceNotOwnedByUserException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetAlreadyExistsForUsernameException;
import dev.prabhjotaulakh.fuel.api.exceptions.SheetNotFoundException;
import dev.prabhjotaulakh.fuel.api.models.Sheet;
import dev.prabhjotaulakh.fuel.api.models.User;
import dev.prabhjotaulakh.fuel.api.repositories.SheetRepository;
import jakarta.transaction.Transactional;

@Service
public class SheetService {
    private final SheetRepository sheetRepository;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;

    public SheetService(SheetRepository sheetRepository, ModelMapper modelMapper, SecurityService securityService) {
        this.sheetRepository = sheetRepository;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
    }

    @Transactional
    public SheetResponse addSheet(String sheetName) {
        var user = securityService.getAuthenticatedUser();
        checkIfAlreadyHasSameSheet(sheetName, user);
        var sheet = new Sheet();
        sheet.setSheetName(sheetName);
        sheet.setLogs(new ArrayList<>());
        sheet.setUser(user);
        sheetRepository.save(sheet);
        return new SheetResponse(sheet.getSheetId(), sheet.getSheetName());
    } 

    public SheetResponse getSheetById(Integer sheetId) {
        var user = securityService.getAuthenticatedUser();
        var sheet = getValidSheet(sheetId);
        checkIfSheetBelongsToUser(sheet, user);
        var sheetResponse = new SheetResponse();
        sheetResponse.setSheetId(sheet.getSheetId());
        sheetResponse.setSheetName(sheet.getSheetName());
        return sheetResponse;
    }

    public List<SheetResponse> getAllSheets() {
        var user = securityService.getAuthenticatedUser();
        var sheets = sheetRepository.getAllSheetsByUserId(user.getUserId());
        var sheetResponseList = sheets.stream()
            .map(sheet -> modelMapper.map(sheet, SheetResponse.class))
            .collect(Collectors.toList());
        return sheetResponseList;
    }

    @Transactional
    public void deleteSheet(Integer sheetId) {
        var sheet = getValidSheet(sheetId);
        var user = securityService.getAuthenticatedUser();
        checkIfSheetBelongsToUser(sheet, user);
        sheetRepository.deleteById(sheet.getSheetId());
    }

    @Transactional
    public void changeSheetName(Integer sheetId, SheetRequest request) {
        var sheet = getValidSheet(sheetId);
        var user = securityService.getAuthenticatedUser();
        checkIfSheetBelongsToUser(sheet, user);
        checkIfAlreadyHasSameSheet(sheet.getSheetName(), user);
        if (sheet.getSheetName().equals(request.getSheetName()))  {
            return;
        }
        sheet.setSheetName(request.getSheetName());
    }

    public Sheet getValidSheet(Integer sheetId) {
        var sheet = sheetRepository.findById(sheetId);
        if (sheet.isEmpty()) {
            throw new SheetNotFoundException();
        }
        return sheet.get();
    }

    public void checkIfSheetBelongsToUser(Sheet sheet, User user) {
        if (sheet.getUser().getUserId() != user.getUserId()) {
            throw new ResourceNotOwnedByUserException();
        }
    }

    private void checkIfAlreadyHasSameSheet(String sheetName, User user) {
        if (sheetRepository.existsBySheetNameAndUserId(sheetName, user.getUserId())) {
            throw new SheetAlreadyExistsForUsernameException(sheetName, user.getUsername());
        }
    }
}
