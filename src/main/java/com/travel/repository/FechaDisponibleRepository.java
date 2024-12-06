package com.travel.repository;

import com.travel.entity.FechaDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FechaDisponibleRepository extends JpaRepository<FechaDisponible, Long> {
}