package com.standby.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.Compartir;

@Repository
public interface CompartirRepository extends JpaRepository<Compartir,UUID>{
    
}
