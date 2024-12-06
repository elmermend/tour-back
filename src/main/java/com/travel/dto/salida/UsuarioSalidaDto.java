package com.travel.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioSalidaDto {
    private String nombre;
    private String apellido;
    private LocalDate fecha;
}

