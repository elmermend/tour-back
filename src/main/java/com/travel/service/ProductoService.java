package com.travel.service;

import java.util.List;

import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.Imagen;
import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
public interface ProductoService {
    List<ProductoSalidaDto> listarTodosLosProductos();
    ProductoSalidaDto crearProducto(ProductoDto productoDTO);
    ProductoSalidaDto listarProductoPorId(Long id);
    void eliminar(Long id);
}

