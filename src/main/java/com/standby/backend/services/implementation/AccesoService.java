package com.standby.backend.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.AccesoCreacionDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.AccesosUsuarios;
import com.standby.backend.models.Residencial;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.AccesoRepository;
import com.standby.backend.repositories.AccesosUsuariosRepository;
import com.standby.backend.repositories.ResidencialRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.IAccesoService;

@Service
public class AccesoService implements IAccesoService {
    private final AccesoRepository accesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AccesosUsuariosRepository accesosUsuariosRepository;
    private final ResidencialRepository residencialRepository;
    private final Mapper mapper;

    public AccesoService(AccesoRepository accesoRepository, Mapper mapper,
            ResidencialRepository residencialRepository,AccesosUsuariosRepository accesosUsuariosRepository,UsuarioRepository usuarioRepository) {
        this.accesoRepository = accesoRepository;
        this.mapper = mapper;
        this.residencialRepository = residencialRepository;
        this.usuarioRepository = usuarioRepository;
        this.accesosUsuariosRepository = accesosUsuariosRepository;
    }

    @Override
    public Acceso obtenerAccesoPorId(UUID idAcceso) {
        Optional<Acceso> rOptional = this.accesoRepository.findById(idAcceso);
        if (rOptional.isEmpty()) {
            throw new RuntimeException("Error: El acceso con el id: " + idAcceso + " no existe");
        }
        return rOptional.get();
    }

    @Override
    public Acceso guardarAcceso(AccesoCreacionDTO accesoCreacionDTO) {
        Optional<Residencial> rOptional = this.residencialRepository
                .findById(UUID.fromString(accesoCreacionDTO.getIdResidencial()));
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: La residencial con el id: " + accesoCreacionDTO.getIdResidencial() + " no existe");
        }
        //ac1eea90-3026-11ee-be56-0242ac120002
        Acceso acceso = accesoRepository.save(mapper.accesoCreationDTOtoAcceso(accesoCreacionDTO));
        //Creando Acceso Garage
        if(accesoCreacionDTO.getUsuarioId() != null){
            accesosUsuariosRepository.save(AccesosUsuarios.builder()
            .id(UUID.randomUUID())
            .isActive(false)
            .usuario(new Usuario(UUID.fromString(accesoCreacionDTO.getUsuarioId())))
            .acceso(acceso)
            .build());
        }
        return acceso;
    }

    @Override
    public List<Acceso> obtenerAccesosPorIdResidencial(UUID idResidencial) {
        return accesoRepository.findAllByResidencialIdResidencial(idResidencial);
    }

    @Override
    public Acceso editarAcceso(AccesoCreacionDTO accesoCreacionDTO, UUID idAcceso) {
        Optional<Acceso> rOptional = this.accesoRepository.findById(idAcceso);
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: El acceso con el id: " + accesoCreacionDTO.getIdResidencial() + " no existe");
        }
       rOptional.get().setDireccion(accesoCreacionDTO.getDireccion());
       rOptional.get().setLatitudCaseta(accesoCreacionDTO.getLatitudCaseta());
       rOptional.get().setLongitudCaseta(accesoCreacionDTO.getLongitudCaseta());
       rOptional.get().setNombre(accesoCreacionDTO.getNombre());
       rOptional.get().setPrecio(accesoCreacionDTO.getPrecio());
       rOptional.get().setRadio(accesoCreacionDTO.getRadio());

       return accesoRepository.save(rOptional.get());
    }

    @Override
    public List<Acceso> obtenerAccesos() {
      return accesoRepository.findAll();
    }

    @Override
    public Acceso cambiarResidencialAcceso(UUID idAcceso, UUID idResidencial) {
        Optional<Acceso> rOptional = this.accesoRepository.findById(idAcceso);
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: El acceso con el id: " + idAcceso + " no existe");
        }
        rOptional.get().setResidencial(new Residencial(idResidencial));
        return accesoRepository.save(rOptional.get());
    }

    @Override
    public Acceso guardarAccesoGarage(AccesoCreacionDTO accesoCreacionDTO) {
        accesoCreacionDTO.setIdResidencial("ac1eea90-3026-11ee-be56-0242ac120002");
        return accesoRepository.save(mapper.accesoCreationDTOtoAcceso(accesoCreacionDTO));
    }

    @Override
    public List<Acceso> obtenerAccesosPorIdUsuario(UUID idUsuario) {
         Optional<Usuario> rOptional = this.usuarioRepository.findById(idUsuario);
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: El usuario con el id: " + idUsuario + " no existe");
        }
        Residencial residencial = rOptional.get().getResidencial();
        return residencial.getAccesos();
    }

}
