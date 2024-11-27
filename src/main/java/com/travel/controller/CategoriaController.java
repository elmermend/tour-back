package com.travel.controller;


import com.travel.dto.entrada.CategoriaDto;
import com.travel.dto.salida.CategoriaSalidaDto;

import com.travel.entity.Categoria;

import com.travel.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/travel/public/categorias")

public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaSalidaDto> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaSalidaDto> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(categoriaService.obtenerCategoriaDtoPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaSalidaDto> crear(@ModelAttribute CategoriaDto categoriaDto) {
        CategoriaSalidaDto nuevaCategoria = categoriaService.crear(categoriaDto);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaSalidaDto> actualizarCategoria(
            @PathVariable Long id,
            @ModelAttribute CategoriaDto categoriaDto) {
        CategoriaSalidaDto categoriaActualizada = categoriaService.actualizar(id, categoriaDto);
        return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            // Llamar al servicio para eliminar la categoría
            categoriaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Eliminar exitosa
        } catch (IllegalStateException e) {
            // Si la categoría tiene productos, se captura la excepción y se envía el mensaje de error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Error con mensaje
        }
    }

}