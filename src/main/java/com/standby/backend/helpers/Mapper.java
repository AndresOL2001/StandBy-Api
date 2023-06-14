package com.standby.backend.helpers;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.standby.backend.DTOs.ResidencialCreacionDTO;
import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.models.Residencial;
import com.standby.backend.models.Rol;
import com.standby.backend.models.Usuario;

@Component
public class Mapper {

    private final PasswordEncoder passwordEncoder;
    public Mapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario usuarioCreationDTOtoUsuario(UsuarioCreationDTO usuario) {
        return Usuario.builder()
        .idUsuario(UUID.randomUUID())
        .calle(usuario.getCalle())
        .celular(usuario.getCelular())
        .numeroCasa(usuario.getNumeroCasa())
        .contraseña(passwordEncoder.encode(usuario.getContraseña()))
        .nombreCompleto(usuario.getNombreCompleto())
        .residencial(new Residencial(UUID.fromString(usuario.getNumeroSerie())))
        .rol(new Rol(2))
        .build();
    }

    public Residencial residencialCreationDTOtoUsuario(ResidencialCreacionDTO residencialCreacionDTO) {
        return Residencial.builder()
        .direccion(residencialCreacionDTO.getDireccion())
        .latitudResidencial(residencialCreacionDTO.getLatitudResidencial())
        .longitudResidencial(residencialCreacionDTO.getLongitudResidencial())
        .numeroSerie(residencialCreacionDTO.getNumeroSerie())
        .build();
    }
    
}
