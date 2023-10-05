package com.standby.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standby.backend.models.Acceso;
import com.standby.backend.models.AccesosUsuarios;
import com.standby.backend.models.Usuario;

public interface AccesosUsuariosRepository extends JpaRepository<AccesosUsuarios,UUID>{
    
    public List<AccesosUsuarios> findAllByUsuario (Usuario usuarioId);
    public AccesosUsuarios findByUsuarioAndAcceso (Usuario usuarioId,Acceso accesoId);

}
