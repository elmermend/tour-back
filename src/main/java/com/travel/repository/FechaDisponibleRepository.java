package com.travel.repository;

import com.travel.entity.FechaDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FechaDisponibleRepository extends JpaRepository<FechaDisponible, Long> {
}