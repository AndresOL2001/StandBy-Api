package com.standby.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.DTOs.VerificarLinkCompartir;
import com.standby.backend.DTOs.creation.AccesoCreacionDTO;
import com.standby.backend.DTOs.creation.CompartirCreacionDTO;
import com.standby.backend.DTOs.creation.LogCreacionDTO;
import com.standby.backend.DTOs.responses.AccesoDTO;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.Compartir;
import com.standby.backend.models.Usuario;
import com.standby.backend.services.implementation.AccesoService;
import com.standby.backend.services.implementation.CompartirService;
import com.standby.backend.services.implementation.LogService;

@RestController
@RequestMapping("/api/accesos")
@CrossOrigin(origins = "*")
public class AccesoController {

    private final AccesoService accesoService;
    private final CompartirService compartirService;
    private final LogService logService;

    public AccesoController(AccesoService accesoService, CompartirService compartirService, LogService logService) {
        this.accesoService = accesoService;
        this.compartirService = compartirService;
        this.logService = logService;
    }

    //acceder Residencial
    @GetMapping("/acceder/{idResidencial}/{idUsuario}")
    public ResponseEntity<?> AccederResidencial(@RequestBody AccesoCreacionDTO accesoCreacionDTO) {
        try {
            ErrorMessage errorMessage = new ErrorMessage("ACCEDIENDO", "OK");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    // Acceder mediante compartidas
    @GetMapping("/acceder/{idResidencial}/{idUsuario}/compartir/{idCompartir}/{idAcceso}")
    public ResponseEntity<?> AccederConCompartidas(@PathVariable String idUsuario, @PathVariable String idResidencial,
            @PathVariable String idCompartir, @PathVariable String idAcceso) {
        try {
            VerificarLinkCompartir verificarLinkCompartir = new VerificarLinkCompartir(idUsuario, idResidencial,
                    idCompartir,idAcceso);
            accesoService.verificarAccesoCompartidas(verificarLinkCompartir);
            logService.crearLog(new LogCreacionDTO(idAcceso,idUsuario),true);
            Acceso acceso = accesoService.obtenerAccesoPorId(UUID.fromString(idAcceso));
            //Lugar donde hacer petición de abrir
            System.out.println(acceso.getEndpoint());
            ErrorMessage errorMessage = new ErrorMessage("ACCEDIENDO", "OK");
            return new ResponseEntity<>(errorMessage, HttpStatus.OK);

        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<?> CrearAcceso(@RequestBody AccesoCreacionDTO accesoCreacionDTO) {
        try {
            Acceso acceso = accesoService.guardarAcceso(accesoCreacionDTO);
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/compartir/comprar/{idUsuario}")
    public ResponseEntity<?> ComprarCompartidas(@PathVariable String idUsuario) {
        try {
            Usuario usuario = accesoService.comprarCompartidas(UUID.fromString(idUsuario));
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/compartir/{idUsuario}/{idResidencial}/{idAcceso}")
    public ResponseEntity<?> CompartirAcceso(@PathVariable String idUsuario, @PathVariable String idResidencial ,
    @PathVariable String idAcceso) {
        try {
            CompartirCreacionDTO compartirCreacionDTO = new CompartirCreacionDTO(idUsuario, idResidencial,idAcceso);
            Compartir compartir = compartirService.crearCompartida(compartirCreacionDTO);
            return new ResponseEntity<>(compartir, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idAcceso}")
    public ResponseEntity<?> editarAcceso(@RequestBody AccesoCreacionDTO accesoCreacionDTO,
            @PathVariable String idAcceso) {
        try {
            Acceso acceso = accesoService.editarAcceso(accesoCreacionDTO, UUID.fromString(idAcceso));
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerAccesoPorId(@PathVariable String id) {

        try {
            Acceso acceso = accesoService.obtenerAccesoPorId(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/residencial/{id}")
    public ResponseEntity<?> ObtenerAccesoPorResidencialId(@PathVariable String id) {

        try {
            List<Acceso> acceso = accesoService.obtenerAccesosPorIdResidencial(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> ObtenerAccesoPorUsuaruioId(@PathVariable String id) {

        try {
            List<AccesoDTO> acceso = accesoService.obtenerAccesosPorIdUsuario(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> obtenerAccesos() {

        try {
            List<Acceso> acceso = accesoService.obtenerAccesos();
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verificar/{idUsuario}")
    public ResponseEntity<Object> login(@PathVariable String idUsuario) {

        try {
            ErrorMessage acceso = accesoService.verificarUsuarioPagos(UUID.fromString(idUsuario));
            return ResponseEntity.ok(acceso);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }
}
