package dev.prabhjotaulakh.fuel.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.prabhjotaulakh.fuel.api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
