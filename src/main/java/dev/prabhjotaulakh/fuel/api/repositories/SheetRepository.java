package dev.prabhjotaulakh.fuel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.prabhjotaulakh.fuel.api.models.Sheet;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Integer> {
    
}
