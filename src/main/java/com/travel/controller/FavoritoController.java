package com.travel.controller;


import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/travel/public/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;


    // Endpoint para agregar un producto como favorito
    @PostMapping("/{productoId}")
    public ResponseEntity<String> agregarFavorito(@PathVariable Long productoId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Intentar guardar el favorito
        boolean fueAgregado = favoritoService.guardarFavorito(currentUserName, productoId);

        // Si fue agregado, retornamos un mensaje con código 201
        if (fueAgregado) {
            return new ResponseEntity<>("Producto agregado a favoritos", HttpStatus.CREATED);
        } else {
            // Si ya existía, retornamos un mensaje con código 200
            return new ResponseEntity<>("Este producto ya es un favorito", HttpStatus.OK);
        }
    }
    // Endpoint para eliminar un producto como favorito
    @DeleteMapping("/{productoId}")
    public ResponseEntity<String> quitarFavorito(@PathVariable Long productoId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        try {
            favoritoService.quitarFavorito(currentUserName, productoId);
            return new ResponseEntity<>("Favorito eliminado exitosamente", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            // En caso de error (producto no encontrado o no es un favorito)
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    // Endpoint para listar Productos favoritos de un usuario registrado con su token
    @GetMapping
    public ResponseEntity<List<ProductoSalidaDto>> obtenerFavoritos() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        List<ProductoSalidaDto> productosFavoritos = favoritoService.obtenerFavoritosPorUsuario(currentUserName);
        return new ResponseEntity<>(productosFavoritos, HttpStatus.OK);
    }
}
