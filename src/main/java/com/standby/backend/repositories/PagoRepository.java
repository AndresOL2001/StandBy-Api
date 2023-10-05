package com.standby.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago,UUID>{
    
}
