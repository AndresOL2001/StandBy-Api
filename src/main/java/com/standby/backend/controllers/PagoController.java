package com.standby.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.DTOs.PagoCreacionDTO;
import com.standby.backend.models.Pago;
import com.standby.backend.services.implementation.PagoService;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
    private final PagoService pagoService;
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

     @PostMapping()
    public ResponseEntity<?> CrearPago(@RequestBody PagoCreacionDTO pagoCreacionDTO){
        try{
            Pago pago = pagoService.guardarPago(pagoCreacionDTO);
            return ResponseEntity.ok(pago);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
