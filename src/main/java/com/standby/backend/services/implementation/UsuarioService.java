package com.standby.backend.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.AccesosUsuarios;
import com.standby.backend.models.Residencial;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.AccesosUsuariosRepository;
import com.standby.backend.repositories.ResidencialRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{

    private final UsuarioRepository usuarioRepository;
        private final ResidencialRepository residencialRepository;
    private final AccesosUsuariosRepository accesosUsuariosRepository;
    private final Mapper mapper;
    public UsuarioService(UsuarioRepository usuarioRepository,
    ResidencialRepository residencialRepository,
    AccesosUsuariosRepository accesosUsuariosRepository,
    Mapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.residencialRepository = residencialRepository;
        this.mapper = mapper;
        this.accesosUsuariosRepository = accesosUsuariosRepository;
    }

    @Override
    public Usuario obtenerUsuarioPorCelular(String celular) {
        Optional<Usuario> uOptional = usuarioRepository.findByCelular(celular);
        if(uOptional.isEmpty()){
            throw new RuntimeException("Error: El usuario con el celular "+celular+" no existe");
        }

        return uOptional.get();
    }

    @Override
    @Transactional
    public Usuario guardarUsuario(UsuarioCreationDTO usuario) {
        Optional<Usuario> uOptional = usuarioRepository.findByCelular(usuario.getCelular());
        if(uOptional.isPresent()){
            throw new RuntimeException("Error: El usuario con el celular "+usuario.getCelular()+" ya existe");
        }

     Optional<Residencial> rOptional = residencialRepository.findByNumeroSerie(usuario.getNumeroSerie());
        if(rOptional.isEmpty()){
            throw new RuntimeException("Error: La residencial con el numero de serie "+usuario.getNumeroSerie()+" no existe");
        }

        usuario.setNumeroSerie(rOptional.get().getIdResidencial().toString());
        Usuario savedUser = usuarioRepository.save(mapper.usuarioCreationDTOtoUsuario(usuario));
        // Call a separate method to handle the AccesosUsuarios creation
        createAccesosUsuarios(savedUser);

        return savedUser;
        
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAccesosUsuarios(Usuario usuario) {
        Residencial residencial = usuario.getResidencial();
        List<Acceso> accesos = residencial.getAccesos();

        for (Acceso acceso : accesos) {
            //Filtrando que a los nuevos usuarios no les aparezcan los garages de los otros usuarios
            if(acceso.isGarage() == false){
                  AccesosUsuarios accesosUsuarios = new AccesosUsuarios(UUID.randomUUID(), usuario, acceso, false);
            accesosUsuariosRepository.save(accesosUsuarios);
            }
          
        }
    }

    @Override
    public Usuario editarUsuario(UsuarioCreationDTO usuario, UUID idUsuario) {
        Optional<Usuario> uOptional = usuarioRepository.findByCelular(usuario.getCelular());
        if(uOptional.isEmpty()){
            throw new RuntimeException("Error: El usuario con el celular "+usuario.getCelular()+" no existe");
        }

        Optional<Residencial> rOptional = residencialRepository.findByNumeroSerie(usuario.getNumeroSerie());
        if(rOptional.isEmpty()){
            throw new RuntimeException("Error: La residencial con el numero de serie "+usuario.getNumeroSerie()+" no existe");
        }
        usuario.setNumeroSerie(rOptional.get().getIdResidencial().toString());
        Usuario usuarioSave = mapper.usuarioCreationDTOtoUsuario(usuario);
        usuarioSave.setIdUsuario(uOptional.get().getIdUsuario());

        return usuarioRepository.save(usuarioSave);
    }

    @Override
    public List<Usuario> obtenerUsuarios(UUID residencial) {
        return usuarioRepository.findByResidencial(new Residencial(residencial)).get();
    }

    @Override
    public Usuario cambiarEstadoPago(UUID idusuario,boolean pago) {
       Optional<Usuario> uOptional = usuarioRepository.findById(idusuario);
        if(uOptional.isEmpty()){
            throw new RuntimeException("Error: El usuario con el id "+idusuario+" no existe");
        }
        uOptional.get().setPago(pago);
        return usuarioRepository.save(uOptional.get());
    }

    @Override
    public void eliminarUsuario(UUID idUsuario) {
      usuarioRepository.deleteById(idUsuario);
    }
    
}
