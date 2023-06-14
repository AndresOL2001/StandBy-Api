package com.standby.backend.services.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Residencial;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.ResidencialRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{

    private final UsuarioRepository usuarioRepository;
        private final ResidencialRepository residencialRepository;

    private final Mapper mapper;
    public UsuarioService(UsuarioRepository usuarioRepository,ResidencialRepository residencialRepository,Mapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.residencialRepository = residencialRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario obtenerUsuarioPorCelular(String celular) {
        Optional<Usuario> uOptional = this.usuarioRepository.findByCelular(celular);
        if(uOptional.isEmpty()){
            throw new RuntimeException("Error: El usuario con el celular "+celular+" no existe");
        }

        return uOptional.get();
    }

    @Override
    public Usuario guardarUsuario(UsuarioCreationDTO usuario) {
        Optional<Usuario> uOptional = this.usuarioRepository.findByCelular(usuario.getCelular());
        if(uOptional.isPresent()){
            throw new RuntimeException("Error: El usuario con el celular "+usuario.getCelular()+" ya existe");
        }

     Optional<Residencial> rOptional = this.residencialRepository.findByNumeroSerie(usuario.getNumeroSerie());
        if(rOptional.isEmpty()){
            throw new RuntimeException("Error: La residencial con el numero de serie "+usuario.getNumeroSerie()+" no existe");
        }

        usuario.setNumeroSerie(rOptional.get().getIdResidencial().toString());
        return usuarioRepository.save(mapper.usuarioCreationDTOtoUsuario(usuario));
        
    }
    
}
