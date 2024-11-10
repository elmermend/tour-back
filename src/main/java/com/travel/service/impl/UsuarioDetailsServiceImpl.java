package com.travel.service.impl;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;
import com.travel.repository.UsuarioRepository;
import com.travel.service.UsuarioDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service   // Indica que esta clase es un servicio de Spring
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Constructor para inyectar el repositorio de Usuario
    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método que carga los detalles del usuario por el nombre de usuario (en este caso, el correo electrónico)
    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email); // Aquí debes definir el método en tu repositorio
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
         // Retorna un objeto UserDetails con los datos del usuario (como el email y la contraseña)
        return usuario;
    }
    @Override
    public Usuario registrarUsuario(Usuario usuario) throws UserAlreadyExistException{
        Usuario usuarioEncontrado = loadUserByUsername(usuario.getEmail() );
        Usuario nuevoUsuario = null;
        if (usuarioEncontrado != null && usuarioEncontrado.getEmail().equals(usuario.getEmail())){
            throw new UserAlreadyExistException("Este usuario ya existe");
        }
        else {
          nuevoUsuario = usuarioRepository.save(usuario);
        }
        return nuevoUsuario;
    }
}
