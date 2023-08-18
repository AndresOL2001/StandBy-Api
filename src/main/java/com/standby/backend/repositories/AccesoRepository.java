package com.standby.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standby.backend.models.Acceso;

public interface AccesoRepository extends JpaRepository<Acceso,UUID>{
    List<Acceso> findAllByResidencialIdResidencial(UUID idResidencial);
}
