package dev.prabhjotaulakh.fuel.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@CrossOrigin(origins = "localhost")
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

    @GetMapping("/private/log/all/{sheetId}")
    public ResponseEntity<List<LogResponse>> getLogsForSheet(@PathVariable Integer sheetId) {
        var sheet = sheetService.getValidSheet(sheetId);
        return ResponseEntity.ok().body(logService.getLogsForAsheet(sheet));
    }

    @GetMapping("/private/log/{logId}")
    public ResponseEntity<LogResponse> getSheetById(@PathVariable Integer logId) {
        return ResponseEntity.ok().body(logService.getLogById(logId));
    }

    @PatchMapping("/private/log/update/{logId}")
    public ResponseEntity<Void> updateLog(@PathVariable Integer logId, @RequestBody @Valid LogRequest request) {
        logService.updateLog(logId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/private/log/delete/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable Integer logId) {
        logService.deleteLog(logId);
        return ResponseEntity.ok().build();
    }
}
