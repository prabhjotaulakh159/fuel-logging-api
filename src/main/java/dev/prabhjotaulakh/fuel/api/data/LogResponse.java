package dev.prabhjotaulakh.fuel.api.data;

import java.time.LocalDateTime;

public class LogResponse {
    private Integer logId;
    private Integer fuelAmount;
    private Integer fuelCost;
    private LocalDateTime localDateTime;
    private LocationResponse location;
    
    public LogResponse() {
    }

    public LogResponse(Integer logId, Integer fuelAmount, Integer fuelCost, LocalDateTime localDateTime,
            LocationResponse location) {
        this.logId = logId;
        this.fuelAmount = fuelAmount;
        this.fuelCost = fuelCost;
        this.localDateTime = localDateTime;
        this.location = location;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Integer fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public Integer getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Integer fuelCost) {
        this.fuelCost = fuelCost;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }
}
