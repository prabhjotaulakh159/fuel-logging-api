package dev.prabhjotaulakh.fuel.api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import dev.prabhjotaulakh.fuel.api.data.LocationResponse;
import dev.prabhjotaulakh.fuel.api.data.LogRequest;
import dev.prabhjotaulakh.fuel.api.data.LogResponse;
import dev.prabhjotaulakh.fuel.api.exceptions.InvalidCountryException;
import dev.prabhjotaulakh.fuel.api.models.Country;
import dev.prabhjotaulakh.fuel.api.models.Location;
import dev.prabhjotaulakh.fuel.api.models.Log;
import dev.prabhjotaulakh.fuel.api.models.Sheet;
import dev.prabhjotaulakh.fuel.api.repositories.LocationRepository;
import dev.prabhjotaulakh.fuel.api.repositories.LogRepository;
import jakarta.transaction.Transactional;

@Service
public class LogService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final LogRepository logRepository;

    public LogService(LocationRepository locationRepository, ModelMapper modelMapper, LogRepository logRepository) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
        this.logRepository = logRepository;
    }

    @Transactional
    public LogResponse createLog(LogRequest request, Sheet sheet) {
        Country country = checkIfCountryExists(request.getCountry());
        var maybeLocation = getPossibleLocation(country, request);
        
        Location location;
        if (maybeLocation.isEmpty()) {
            location = createLocation(request, country);
            locationRepository.save(location);
        } else {
            location = maybeLocation.get();
        }

        var log = createLog(request, sheet, location);
        logRepository.save(log);
        
        var locationResponse = modelMapper.map(location, LocationResponse.class);
        
        var logResponse = new LogResponse();
        logResponse.setLogId(log.getLogId());
        logResponse.setFuelAmount(log.getFuelAmount());
        logResponse.setFuelCost(log.getFuelCost());
        logResponse.setLocalDateTime(log.getLocalDateTime());
        logResponse.setLocation(locationResponse);
        return logResponse;        
    }

    private Log createLog(LogRequest request, Sheet sheet, Location location) {
        var log = new Log();
        log.setSheet(sheet);
        log.setFuelAmount(request.getFuelAmount());
        log.setFuelCost(request.getFuelCost());
        log.setLocalDateTime(request.getLocalDateTime());
        log.setLocation(location);
        return log;
    }

    private Location createLocation(LogRequest request, Country country) {
        Location location = new Location();
        location.setCountry(country);
        location.setState(request.getState().toUpperCase());
        location.setDoorNumber(request.getDoorNumber());
        location.setPostalCode(request.getPostalCode().toUpperCase());
        location.setLogs(new ArrayList<>());
        return location;
    }

    private Optional<Location> getPossibleLocation(Country country, LogRequest request) {
        return locationRepository.findByCountryAndStateAndPostalCodeAndDoorNumber(
            country, request.getState(), request.getPostalCode(), request.getDoorNumber());
    }

    private Country checkIfCountryExists(String country) {
        try {
            return Country.valueOf(country.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new InvalidCountryException(country);
        }
    }
    
}
