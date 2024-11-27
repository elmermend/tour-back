package com.travel.dto.entrada;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private double precio;
    private Long categoriaId; // ID de la categoría
    private List<Long> caracteristicaIds; // IDs de las características
    private List<MultipartFile> imagenes;

    private List<FechaDisponibleDto> fechasDisponibles;
}
