package dev.prabhjotaulakh.fuel.api.data;

import jakarta.validation.constraints.NotBlank;

public class SheetRequest {
    @NotBlank(message = "Sheet name cannot be null")
    private String sheetName;

    public SheetRequest() {
    }

    public SheetRequest(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
