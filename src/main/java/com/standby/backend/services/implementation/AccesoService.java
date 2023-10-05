package com.standby.backend.services.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.DTOs.VerificarLinkCompartir;
import com.standby.backend.DTOs.creation.AccesoCreacionDTO;
import com.standby.backend.DTOs.responses.AccesoDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.AccesosUsuarios;
import com.standby.backend.models.Compartir;
import com.standby.backend.models.Residencial;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.AccesoRepository;
import com.standby.backend.repositories.AccesosUsuariosRepository;
import com.standby.backend.repositories.CompartirRepository;
import com.standby.backend.repositories.ResidencialRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.IAccesoService;

@Service
public class AccesoService implements IAccesoService {
    private final AccesoRepository accesoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AccesosUsuariosRepository accesosUsuariosRepository;
    private final CompartirRepository compartirRepository;
    private final ResidencialRepository residencialRepository;
    private final Mapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(AccesoService.class);

    public AccesoService(AccesoRepository accesoRepository, Mapper mapper,
            ResidencialRepository residencialRepository, AccesosUsuariosRepository accesosUsuariosRepository,
            UsuarioRepository usuarioRepository, CompartirRepository compartirRepository) {
        this.accesoRepository = accesoRepository;
        this.mapper = mapper;
        this.residencialRepository = residencialRepository;
        this.usuarioRepository = usuarioRepository;
        this.accesosUsuariosRepository = accesosUsuariosRepository;
        this.compartirRepository = compartirRepository;
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
        // ac1eea90-3026-11ee-be56-0242ac120002
        Acceso acceso = accesoRepository.save(mapper.accesoCreationDTOtoAcceso(accesoCreacionDTO));
        // Creando Acceso Garage
        if (accesoCreacionDTO.getUsuarioId() != null) {
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
    public List<AccesoDTO> obtenerAccesosPorIdUsuario(UUID idUsuario) {
        List<AccesoDTO> listAccesoDTOs = new ArrayList<>();
        List<AccesosUsuarios> listAccesosUsuarios = accesosUsuariosRepository.findAllByUsuario(new Usuario(idUsuario));
        Optional<Usuario> rOptional = this.usuarioRepository.findById(idUsuario);
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: El usuario con el id: " + idUsuario + " no existe");
        }
        Residencial residencial = rOptional.get().getResidencial();
        List<Acceso> accesos = residencial.getAccesos();

        // Create a map to store AccesosUsuarios by Acceso id
        Map<UUID, AccesosUsuarios> accesoUsuariosMap = new HashMap<>();
        for (AccesosUsuarios accesoUsuario : listAccesosUsuarios) {
            accesoUsuariosMap.put(accesoUsuario.getAcceso().getIdAcceso(), accesoUsuario);
        }

        for (Acceso acceso : accesos) {
            AccesoDTO accesoDTO = new AccesoDTO();

            // Set properties from Acceso
            accesoDTO.setIdAcceso(acceso.getIdAcceso());
            accesoDTO.setLongitudCaseta(acceso.getLongitudCaseta());
            accesoDTO.setLatitudCaseta(acceso.getLatitudCaseta());
            accesoDTO.setDireccion(acceso.getDireccion());
            accesoDTO.setPrecio(acceso.getPrecio());
            accesoDTO.setGarage(acceso.isGarage());
            accesoDTO.setNombre(acceso.getNombre());
            accesoDTO.setRadio(acceso.getRadio());

            // Set properties from AccesosUsuarios if available
            AccesosUsuarios accesoUsuario = accesoUsuariosMap.get(acceso.getIdAcceso());
            if (accesoUsuario != null) {
                accesoDTO.setActive(accesoUsuario.isActive());
                listAccesoDTOs.add(accesoDTO);
            }

        }
        return listAccesoDTOs;
    }

    @Override
    public ErrorMessage verificarUsuarioPagos(UUID idUsuario) {
        Optional<Usuario> rOptional = this.usuarioRepository.findById(idUsuario);
        if (rOptional.isEmpty()) {
            throw new RuntimeException(
                    "Error: El usuario con el id: " + idUsuario + " no existe");
        }
        List<AccesosUsuarios> listAccesosUsuarios = accesosUsuariosRepository.findAllByUsuario(new Usuario(idUsuario));
        for (AccesosUsuarios accesosUsuarios : listAccesosUsuarios) {
            if (!accesosUsuarios.isActive()) {
                ErrorMessage message = new ErrorMessage(
                        "Error: Este usuario no ha pagado el acceso " + accesosUsuarios.getAcceso().getNombre()
                                + " no ha sido pagado",
                        "BAD_REQUEST");
                return message;
            }
        }
        ErrorMessage message = new ErrorMessage(
                "Usuario al corriente", "OK");

        return message;
    }

    @Override
    @Transactional
    public void verificarAccesoCompartidas(VerificarLinkCompartir verificarLinkCompartir) {
        Optional<Usuario> uOptional = usuarioRepository
                .findById(UUID.fromString(verificarLinkCompartir.getIdUsuario()));
        if (uOptional.isEmpty()) {
            logger.error("AccesoService verificarAccesoCompartidas id de usuario no encontrado");
            throw new RuntimeException("Error: id de usuario no encontrado");
        }

        Optional<Residencial> rOptional = residencialRepository
                .findById(UUID.fromString(verificarLinkCompartir.getIdResidencial()));
        if (rOptional.isEmpty()) {
            logger.error("AccesoService verificarAccesoCompartidas id de residencial no encontrado");
            throw new RuntimeException("Error: id de residencial no encontrado");
        }

        Optional<Compartir> cOptional = compartirRepository
                .findById(UUID.fromString(verificarLinkCompartir.getIdCompartir()));
        if (cOptional.isEmpty()) {
            logger.error("AccesoService verificarAccesoCompartidas id de compartir no encontrado");
            throw new RuntimeException("Error: id de compartir no encontrado");
        }

        Optional<Acceso> aOptional = accesoRepository
                .findById(UUID.fromString(verificarLinkCompartir.getIdAcceso()));

        if (aOptional.isEmpty()) {
            logger.error("AccesoService verificarAccesoCompartidas id de acceso no encontrado");
            throw new RuntimeException("Error: id de acceso no encontrado");
        }

        if (cOptional.get().hasExpired()) {
            cOptional.get().setEstaActivo(false);
            compartirRepository.save(cOptional.get());
            logger.error("AccesoService verificarAccesoCompartidas Link Expirado");
            throw new RuntimeException("Error: Link Expirado");
        }

    }

}
