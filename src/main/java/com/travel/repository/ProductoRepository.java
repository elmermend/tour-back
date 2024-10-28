package com.travel.repository;

import org.springframework.data.repository.CrudRepository;
import com.travel.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

  Producto findById(long id);

  Producto findByNombre(String nombre);
}