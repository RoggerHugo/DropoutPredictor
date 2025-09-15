package com.crai.ia.dropoutpredictor.repository;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.crai.ia.dropoutpredictor.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>, JpaSpecificationExecutor<Alumno> {
  boolean existsByMatricula(String matricula);
}