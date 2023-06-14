package com.standby.backend.security.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.UsuarioDetallesDTO;
import com.standby.backend.models.Usuario;
import com.standby.backend.services.implementation.UsuarioService;

@Service
public class UsuarioDetalleService implements UserDetailsService{

    private final UsuarioService userService;
    
    @Autowired
    public UsuarioDetalleService(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Usuario user = userService.obtenerUsuarioPorCelular(userName);
        if(user == null){
            throw new RuntimeException("Error no existe ningún usuario con el correo: "+userName);
        }
        return UsuarioDetallesDTO.build(user);
    }
}