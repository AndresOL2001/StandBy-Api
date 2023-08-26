package com.standby.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standby.backend.models.AccesosUsuarios;

public interface AccesosUsuariosRepository extends JpaRepository<AccesosUsuarios,UUID>{
    
}
