package com.travel.service;

import com.travel.dto.salida.ReservaSalidaDto;
import com.travel.dto.salida.UsuarioSalidaDto;

import java.util.List;

public interface ReservaService {
    ReservaSalidaDto crearReserva(Long fechaDisponibleId, String emailUsuario);
    List<UsuarioSalidaDto> obtenerUsuariosPorFecha(Long fechaDisponibleId);
    List<ReservaSalidaDto> obtenerReservasPorUsuario(String emailUsuario);
}
