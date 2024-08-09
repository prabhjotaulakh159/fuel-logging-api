package dev.prabhjotaulakh.fuel.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.prabhjotaulakh.fuel.api.models.Country;
import dev.prabhjotaulakh.fuel.api.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByCountryAndStateAndPostalCodeAndDoorNumber(
        Country country,
        String state,
        String postalCode,
        Integer doorNumber
    );
}
