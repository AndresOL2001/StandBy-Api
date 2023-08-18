package com.standby.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.DTOs.ResidencialCreacionDTO;
import com.standby.backend.models.Residencial;
import com.standby.backend.services.implementation.ResidencialService;

@RestController
@RequestMapping("/api/residencial")
@CrossOrigin(origins = "*")
public class ResidencialController {
     private final ResidencialService residencialService;

    public ResidencialController(ResidencialService residencialService) {
        this.residencialService = residencialService;
    }

    @PostMapping()
    public ResponseEntity<?> CrearResidencial(@RequestBody ResidencialCreacionDTO residencialCreacionDTO){
        
        try{
            Residencial residencial = residencialService.guardarResidencial(residencialCreacionDTO);
            return ResponseEntity.ok(residencial);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
       

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerResidencialPorId(@PathVariable String id){
        
        try{
            Residencial residencial = residencialService.obtenerResidencialPorId(UUID.fromString(id));
            return ResponseEntity.ok(residencial);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> EditarResidencialPorId(@RequestBody ResidencialCreacionDTO residencialCreacionDTO,@PathVariable String id){
        
        try{
            Residencial residencial = residencialService.editarResidencial(residencialCreacionDTO,UUID.fromString(id));
            return ResponseEntity.ok(residencial);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> obtenerResidenciales(){
        
        try{
            List<Residencial> residencial = residencialService.obtenerResidenciales();
            return ResponseEntity.ok(residencial);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResidencial(@PathVariable String id){
        
        try{
            ErrorMessage errorMessage = new ErrorMessage("Correcto", "OK");
            residencialService.eliminarResidencial(UUID.fromString(id));
            return ResponseEntity.ok(errorMessage);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
