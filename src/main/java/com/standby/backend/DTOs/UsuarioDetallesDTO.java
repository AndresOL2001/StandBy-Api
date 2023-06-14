package com.standby.backend.DTOs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.standby.backend.models.Usuario;

public class UsuarioDetallesDTO implements UserDetails{

    private String celular;
    private String contraseña;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioDetallesDTO(String celular, String password,
    Collection<? extends GrantedAuthority> authorities) {
        this.celular = celular;
        this.contraseña = password;
        this.authorities = authorities;
        }

    public static UsuarioDetallesDTO build(Usuario usuario) {
        List<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getName()));
        return new UsuarioDetallesDTO(usuario.getCelular(), usuario.getContraseña(), authorities);
      }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return celular;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}

