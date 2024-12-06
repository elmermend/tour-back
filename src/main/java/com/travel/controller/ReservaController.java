package com.travel.controller;

import com.travel.dto.salida.ReservaSalidaDto;
import com.travel.dto.salida.UsuarioSalidaDto;
import com.travel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel/public/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaSalidaDto> crearReserva(
            @RequestParam Long fechaDisponibleId) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        ReservaSalidaDto reservaSalidaDto = reservaService.crearReserva(fechaDisponibleId, currentUserName);
        return new ResponseEntity<>(reservaSalidaDto, HttpStatus.CREATED);
    }
    //Lista de usuarios que tambien hicieron Reserva la misma fecha
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioSalidaDto>> obtenerUsuariosPorFecha(
            @RequestParam Long fechaDisponibleId) {
        List<UsuarioSalidaDto> usuarios = reservaService.obtenerUsuariosPorFecha(fechaDisponibleId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaSalidaDto>> obtenerReservasUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();// Decodifica el token para obtener el email del usuario
        List<ReservaSalidaDto> reservas = reservaService.obtenerReservasPorUsuario(currentUserName);
        return ResponseEntity.ok(reservas);
    }

}