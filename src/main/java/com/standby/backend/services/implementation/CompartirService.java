package com.standby.backend.services.implementation;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.creation.CompartirCreacionDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Compartir;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.CompartirRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.ICompartirService;

@Service
public class CompartirService implements ICompartirService{


    private final CompartirRepository compartirRepository;
    private final UsuarioRepository usuarioRepository;
    private final Mapper mapper;
    public CompartirService(CompartirRepository compartirRepository,Mapper mapper,UsuarioRepository usuarioRepository) {
        this.compartirRepository = compartirRepository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Compartir crearCompartida(CompartirCreacionDTO compartir) {
        Optional<Usuario> oUsuario = usuarioRepository.findById(UUID.fromString(compartir.getIdUsuario()));
        if(oUsuario.isPresent()){
            oUsuario.get().setCompartidas(oUsuario.get().getCompartidas() - 1);
            usuarioRepository.save(oUsuario.get());
        }
        return compartirRepository.save(this.mapper.compartirCreacionDTOtoCompartir(compartir));
    }
    
}
