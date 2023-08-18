package com.standby.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.AccesosUsuarios;

public interface AccesosUsuariosRepository extends JpaRepository<AccesosUsuarios,UUID>{
    
}
