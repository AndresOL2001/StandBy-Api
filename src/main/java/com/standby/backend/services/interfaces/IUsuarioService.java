package com.standby.backend.services.interfaces;

import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.models.Usuario;

public interface IUsuarioService {
    public Usuario obtenerUsuarioPorCelular(String celular);
    public Usuario guardarUsuario(UsuarioCreationDTO usuario);

}
