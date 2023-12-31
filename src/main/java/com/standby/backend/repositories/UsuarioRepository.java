package com.standby.backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.standby.backend.models.Residencial;
import com.standby.backend.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,UUID>{
        Optional<Usuario> findByCelular(String celular);
        Optional<List<Usuario>> findByResidencial(Residencial residencial);

}
