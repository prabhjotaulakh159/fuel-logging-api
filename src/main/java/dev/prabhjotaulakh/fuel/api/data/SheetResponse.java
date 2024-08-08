package dev.prabhjotaulakh.fuel.api.data;

public class SheetResponse {
    private Integer sheetId;
    private String sheetName;
    
    public SheetResponse() {
    }

    public SheetResponse(Integer sheetId, String sheetName) {
        this.sheetId = sheetId;
        this.sheetName = sheetName;
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
}
