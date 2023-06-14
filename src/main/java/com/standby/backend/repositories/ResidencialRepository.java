package com.standby.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.Residencial;

@Repository
public interface ResidencialRepository extends JpaRepository<Residencial, UUID> {
    Optional<Residencial> findByNumeroSerie(String numeroSerie);

}
