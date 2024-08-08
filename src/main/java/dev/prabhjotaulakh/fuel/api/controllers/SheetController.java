package dev.prabhjotaulakh.fuel.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.prabhjotaulakh.fuel.api.data.SheetRequest;
import dev.prabhjotaulakh.fuel.api.data.SheetResponse;
import dev.prabhjotaulakh.fuel.api.services.SheetService;
import jakarta.validation.Valid;

@RestController
public class SheetController {
    private final SheetService sheetService;

    public SheetController(SheetService sheetService) {
        this.sheetService = sheetService;
    }

    @PostMapping("/private/sheet/create")
    public ResponseEntity<SheetResponse> createSheet(@RequestBody @Valid SheetRequest sheetRequest) {
        var sheetResponse = sheetService.addSheet(sheetRequest.getSheetName()); 
        return ResponseEntity.ok().body(sheetResponse);
    }

    @GetMapping("/private/sheet/{sheetId}")
    public ResponseEntity<SheetResponse> getSingleSheet(@PathVariable Integer sheetId) {
        return ResponseEntity.ok().body(sheetService.getSheetById(sheetId));
    }
}
