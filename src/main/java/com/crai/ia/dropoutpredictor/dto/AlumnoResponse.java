package com.crai.ia.dropoutpredictor.dto;

import java.time.*;

public record AlumnoResponse(
  Long id,
  String nombreCompleto,
  String matricula,
  LocalDate fechaNacimiento,
  Integer edad,
  Long estadoCivilId,
  String estadoCivil,
  Long turnoId,
  String turno,
  Long carreraId,
  String carrera,
  Boolean activo,
  OffsetDateTime creadoEn
) {}