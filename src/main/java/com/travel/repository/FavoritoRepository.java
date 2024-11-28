package com.travel.repository;

import com.travel.entity.Favorito;
import com.travel.entity.Producto;
import com.travel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(UserEntity usuario);
    Optional<Favorito> findByUsuarioAndProducto(UserEntity usuario, Producto producto);
}