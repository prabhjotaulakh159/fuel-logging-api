package dev.prabhjotaulakh.fuel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.prabhjotaulakh.fuel.api.models.Sheet;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Integer> {
    // checks if a user already has a sheet with a particular name 
    @Query(value = 
    "SELECT COUNT(*) > 0 " +
    "FROM sheets " +
    "INNER JOIN users ON sheets.user_id = users.user_id " +
    "WHERE users.username = :username AND sheets.sheet_name = :sheet_name", 
    nativeQuery = true)
    boolean existsBySheetNameAndUsername(@Param("sheet_name")String sheetName, @Param("username")String username);
}
