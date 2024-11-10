package com.travel.repository;

import com.travel.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository   // Indica que esta es una interfaz de repositorio
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

     // Método para encontrar un usuario por su email
    Usuario findByEmail(String email); // Método para buscar por correo electrónico
}