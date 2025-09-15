package com.crai.ia.dropoutpredictor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crai.ia.dropoutpredictor.entity.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {}