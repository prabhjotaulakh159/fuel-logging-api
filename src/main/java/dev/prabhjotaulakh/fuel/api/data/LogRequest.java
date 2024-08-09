package dev.prabhjotaulakh.fuel.api.data;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public class LogRequest {
    @Min(value = 1, message = "Fuel amount must be greater than 0")
    private Integer fuelAmount;

    @Min(value = 0, message = "Fuel cost must not be negative")
    private Integer fuelCost;

    @PastOrPresent(message = "Fueling time cannot be in the future")
    private LocalDateTime localDateTime;

    @NotBlank(message = "Country cannot be blank")
    private String country;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @NotBlank(message = "Postal code cannot be blank")
    private String postalCode;

    @Min(value = 0, message = "Door number cannot be negative")
    private Integer doorNumber;

    public LogRequest() {
    }

    public LogRequest(Integer fuelAmount, Integer fuelCost, LocalDateTime localDateTime, String country, String state, String postalCode, Integer doorNumber) {
        this.fuelAmount = fuelAmount;
        this.fuelCost = fuelCost;
        this.localDateTime = localDateTime;
        this.country = country;
        this.state = state;
        this.postalCode = postalCode;
        this.doorNumber = doorNumber;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer doorNumber) {
        this.doorNumber = doorNumber;
    }   
}
