package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.models.Usuario;

public interface IUsuarioService {
    public Usuario obtenerUsuarioPorCelular(String celular);

    public Usuario guardarUsuario(UsuarioCreationDTO usuario);

    public Usuario editarUsuario(UsuarioCreationDTO usuario, UUID idUsuario);

    public List<Usuario> obtenerUsuarios(UUID residencial);

    public Usuario cambiarEstadoPago(UUID idusuario,boolean pago);

    public void eliminarUsuario(UUID idUsuario);

}
