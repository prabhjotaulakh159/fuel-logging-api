package dev.prabhjotaulakh.fuel.api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    @Column(nullable = false)
    private Integer fuelAmount;

    @Column(nullable = false)
    private Integer fuelCost;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    public Log() {
    }

    public Log(Integer logId, Sheet sheet, Integer fuelAmount, Integer fuelCost, LocalDateTime localDateTime,
            Location location) {
        this.logId = logId;
        this.sheet = sheet;
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

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
