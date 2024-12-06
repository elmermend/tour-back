package com.travel.repository;

import com.travel.entity.FechaDisponible;
import com.travel.entity.Reserva;
import com.travel.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsByFechaTourAndUsuario(FechaDisponible fechaTour, UserEntity usuario);
    List<Reserva> findByFechaTourId(Long fechaTourId);
    List<Reserva> findByUsuario(UserEntity usuario);

}
