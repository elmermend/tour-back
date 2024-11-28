package com.travel.service;

import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.Favorito;
import com.travel.entity.UserEntity;
import com.travel.entity.Producto;

import java.util.List;

public interface FavoritoService {
    boolean guardarFavorito(String username, Long productoId);

    List<ProductoSalidaDto> obtenerFavoritosPorUsuario(String currentUserName);
    void quitarFavorito(String currentUserName, Long productoId);
}