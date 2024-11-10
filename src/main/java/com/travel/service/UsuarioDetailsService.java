package com.travel.service;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;

public interface UsuarioDetailsService {
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException;
    
    public Usuario registrarUsuario(Usuario usuario) throws UserAlreadyExistException;
}