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

import com.standby.backend.DTOs.AccesoCreacionDTO;
import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.models.Acceso;
import com.standby.backend.services.implementation.AccesoService;

@RestController
@RequestMapping("/api/accesos")
@CrossOrigin(origins = "*")
public class AccesoController {

    private final AccesoService accesoService;
    public AccesoController(AccesoService accesoService) {
        this.accesoService = accesoService;
    }

    @PostMapping()
    public ResponseEntity<?> CrearAcceso(@RequestBody AccesoCreacionDTO accesoCreacionDTO){
        try{
            Acceso acceso = accesoService.guardarAcceso(accesoCreacionDTO);
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{idAcceso}")
    public ResponseEntity<?> editarAcceso(@RequestBody AccesoCreacionDTO accesoCreacionDTO,@PathVariable String idAcceso){
        try{
            Acceso acceso = accesoService.editarAcceso(accesoCreacionDTO,UUID.fromString(idAcceso));
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerAccesoPorId(@PathVariable String id){
        
        try{
            Acceso acceso = accesoService.obtenerAccesoPorId(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/residencial/{id}")
    public ResponseEntity<?> ObtenerAccesoPorResidencialId(@PathVariable String id){
        
        try{
            List<Acceso> acceso = accesoService.obtenerAccesosPorIdResidencial(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> ObtenerAccesoPorUsuaruioId(@PathVariable String id){
        
        try{
            List<Acceso> acceso = accesoService.obtenerAccesosPorIdUsuario(UUID.fromString(id));
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> obtenerAccesos(){
        
        try{
            List<Acceso> acceso = accesoService.obtenerAccesos();
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
