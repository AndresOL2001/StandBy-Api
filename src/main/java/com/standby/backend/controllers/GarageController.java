package com.standby.backend.controllers;
import java.util.List;

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
import com.standby.backend.models.Acceso;
import com.standby.backend.services.implementation.GarageService;

@RestController
@RequestMapping("/api/garages")
@CrossOrigin(origins = "*")
public class GarageController {
    private final GarageService garageService;
    public GarageController(GarageService garageService) {
       this.garageService = garageService;
    }

    @GetMapping()
    public ResponseEntity<?> obtenerGarages(){
        
        try{
            List<Acceso> acceso = garageService.obtenerGarages();
            return ResponseEntity.ok(acceso);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
