package dev.prabhjotaulakh.fuel.api.data;

import java.util.List;

import dev.prabhjotaulakh.fuel.api.models.Log;

public class SheetResponse {
    private Integer sheetId;
    private String sheetName;
    private List<Log> logs;
    
    public SheetResponse() {
    }

    public SheetResponse(Integer sheetId, String sheetName, List<Log> logs) {
        this.sheetId = sheetId;
        this.sheetName = sheetName;
        this.logs = logs;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) {
        this.sheetId = sheetId;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
