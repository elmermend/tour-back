package com.travel.service.impl;

import com.travel.dto.salida.ReservaSalidaDto;
import com.travel.dto.salida.UsuarioSalidaDto;
import com.travel.entity.FechaDisponible;
import com.travel.entity.Reserva;
import com.travel.entity.UserEntity;
import com.travel.exception.NotFoundException;
import com.travel.repository.FechaDisponibleRepository;
import com.travel.repository.ReservaRepository;
import com.travel.repository.UserRepository;
import com.travel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final FechaDisponibleRepository fechaDisponibleRepository;
    private final UserRepository usuarioRepository;

    @Autowired
    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              FechaDisponibleRepository fechaDisponibleRepository,
                              UserRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.fechaDisponibleRepository = fechaDisponibleRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ReservaSalidaDto crearReserva(Long fechaDisponibleId, String emailUsuario) {
        // Obtener usuario autenticado
        UserEntity usuario = usuarioRepository.findByUsername(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener fecha disponible
        FechaDisponible fechaDisponible = fechaDisponibleRepository.findById(fechaDisponibleId)
                .orElseThrow(() -> new NotFoundException("Fecha disponible no encontrada"));

        // Verificar stock
        if (!fechaDisponible.tieneStock()) {
            throw new IllegalStateException("No hay stock disponible para esta fecha");
        }

        // Verifica si el usuario ya tiene una reserva para esta fecha
        if (reservaRepository.existsByFechaTourAndUsuario(fechaDisponible, usuario)) {
            throw new IllegalArgumentException("Ya tienes una reserva para esta fecha.");
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setFecha(LocalDate.now());
        reserva.setFechaTour(fechaDisponible);
        reserva.setUsuario(usuario);

        // Incrementar disponibilidad
        fechaDisponible.incrementarDisponibilidad();
        fechaDisponibleRepository.save(fechaDisponible);

        // Guardar reserva
        Reserva reservaGuardada = reservaRepository.save(reserva);
        return mapearReservaAReservaSalidaDto(reservaGuardada);
    }

    private ReservaSalidaDto mapearReservaAReservaSalidaDto(Reserva reserva) {
        ReservaSalidaDto dto = new ReservaSalidaDto();
        dto.setId(reserva.getId());
        dto.setFechaTour(reserva.getFechaTour().getFecha());
        dto.setFecha(reserva.getFecha());
        dto.setDuracionDias(reserva.getFechaTour().getDuracionDias());
        dto.setNombreProducto(reserva.getFechaTour().getProducto().getNombre());
        return dto;
    }



    public List<UsuarioSalidaDto> obtenerUsuariosPorFecha(Long fechaDisponibleId) {
        // Obtener las reservas de la fecha disponible
        List<Reserva> reservas = reservaRepository.findByFechaTourId(fechaDisponibleId);

        // Mapear usuarios a UsuarioSalidaDto
        return reservas.stream()
                .map(reserva -> {
                    UserEntity usuario = reserva.getUsuario();
                    return new UsuarioSalidaDto(
                            usuario.getNombre(),
                            usuario.getApellido(),
                            usuario.getFecha()
                    );
                })
                .collect(Collectors.toList());
    }

    public List<ReservaSalidaDto> obtenerReservasPorUsuario(String emailUsuario) {
        UserEntity usuario = usuarioRepository.findByUsername(emailUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        List<Reserva> reservas = reservaRepository.findByUsuario(usuario);

        return reservas.stream()
                .map(reserva -> new ReservaSalidaDto(
                        reserva.getId(),
                        reserva.getFechaTour().getFecha(),
                        reserva.getFecha(),
                        reserva.getFechaTour().getDuracionDias(),
                        reserva.getFechaTour().getProducto().getNombre()
                ))
                .collect(Collectors.toList());

    }

}
