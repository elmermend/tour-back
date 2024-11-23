package com.travel.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.travel.entity.Imagen;

public interface ImagenRepository extends CrudRepository<Imagen,Long> {
    Optional<Imagen> findByUrl(String nombre);
}