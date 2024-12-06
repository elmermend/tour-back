package com.travel.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaSalidaDto {
    private Long id; // ID de la reserva
    private LocalDate fechaTour; // Fecha del tour reservado
    private LocalDate fecha; //Fecha que realizó la reserva
    private int duracionDias; // Duración del tour en días
    private String nombreProducto; // Nombre del producto
}
