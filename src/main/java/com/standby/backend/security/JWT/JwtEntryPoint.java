package com.standby.backend.security.JWT;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    //Controlamos error que se devuelve cuando un usuario sin rol intenta realizar alguna accion
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
    }
}