package com.standby.backend.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
import com.standby.backend.DTOs.EstadoDTO;
import com.standby.backend.DTOs.JwtDTO;
import com.standby.backend.DTOs.UsuarioCreationDTO;
import com.standby.backend.DTOs.UsuarioLoginDTO;
import com.standby.backend.models.Usuario;
import com.standby.backend.security.JWT.JwtProvider;
import com.standby.backend.services.implementation.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    public AuthController(UsuarioService usuarioService, AuthenticationManagerBuilder authenticationManagerBuilder,
            JwtProvider jwtProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/cambiarestadopago/usuario/{id}")
    public ResponseEntity<?> cambiarEstadoUsuario(@PathVariable String id, @RequestBody EstadoDTO estadoDTO) {
        try {
            Usuario usuarioResponse = usuarioService.cambiarEstadoPago(UUID.fromString(id), estadoDTO.isEstado());
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{celular}")
    public ResponseEntity<?> ObtenerUsuarioPorCelular(@PathVariable String celular) {

        try {
            Usuario usuarioResponse = usuarioService.obtenerUsuarioPorCelular(celular);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String id) {

        try {
            ErrorMessage errorMessage = new ErrorMessage("Correcto", "OK");

            usuarioService.eliminarUsuario(UUID.fromString(id));
            return ResponseEntity.ok(errorMessage);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/usuarios/{residencial}")
    public ResponseEntity<?> ObtenerUsuariosPorResidencial(@PathVariable String residencial) {

        try {
            List<Usuario> usuarioResponse = usuarioService.obtenerUsuarios(UUID.fromString(residencial));
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> CrearUsuario(@RequestBody UsuarioCreationDTO usuarioDTO) {

        try {
            Usuario usuarioResponse = usuarioService.guardarUsuario(usuarioDTO);
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> ActualizarUsuario(@RequestBody UsuarioCreationDTO usuarioDTO, @PathVariable String id) {

        try {
            Usuario usuarioResponse = usuarioService.editarUsuario(usuarioDTO, UUID.fromString(id));
            return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), "BAD_REQUEST");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UsuarioLoginDTO loginUser, BindingResult bidBindingResult) {
        // Comprobar Cuenta Activada
        JwtDTO jwtDto;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser.getCelular(), loginUser.getContraseña());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            jwtDto = new JwtDTO(jwt);
        } catch (Exception e) {
            ErrorMessage eMessage = new ErrorMessage();
            eMessage.setMensaje(e.getMessage());
            eMessage.setStatus("BAD_REQUEST");
            return new ResponseEntity<>(eMessage, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);

    }

   
}
