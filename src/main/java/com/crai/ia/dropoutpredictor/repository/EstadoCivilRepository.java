package com.crai.ia.dropoutpredictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crai.ia.dropoutpredictor.entity.EstadoCivil;

public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {
}