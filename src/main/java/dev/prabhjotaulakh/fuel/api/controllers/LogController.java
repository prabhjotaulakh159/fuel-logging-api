package dev.prabhjotaulakh.fuel.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.prabhjotaulakh.fuel.api.data.LogRequest;
import dev.prabhjotaulakh.fuel.api.data.LogResponse;
import dev.prabhjotaulakh.fuel.api.services.LogService;
import dev.prabhjotaulakh.fuel.api.services.SheetService;
import jakarta.validation.Valid;

@RestController
public class LogController {
    private final LogService logService;
    private final SheetService sheetService;

    public LogController(LogService logService, SheetService sheetService) {
        this.logService = logService;
        this.sheetService = sheetService;
    }

    @PostMapping("/private/log/create/{sheetId}")
    public ResponseEntity<LogResponse> createLog(@PathVariable Integer sheetId, @RequestBody @Valid LogRequest request) {
        var sheet = sheetService.getValidSheet(sheetId);
        return ResponseEntity.ok().body(logService.createLog(request, sheet));
    }
}
