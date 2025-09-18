package com.crai.ia.dropoutpredictor.dto;

import com.crai.ia.dropoutpredictor.entity.StudentDetailProjection;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public record AlumnoDetailResponse(
  Long alumnoId,
  String nombreCompleto,
  String matricula,
  Boolean activo,
  OffsetDateTime creadoEn,

  String estadoCivil,
  String turno,
  String carrera,

  Integer minutosDetalle,
  BigDecimal promedioDetalle,
  Boolean tieneHijos,
  Boolean cuentaEquipoComputo,
  String viveCon,
  String apoyoFinanciero,
  String situacionActual,
  String situacionEstudiantil,
  String observacionesDetalle,

  Integer minutosEncuesta,
  BigDecimal promedioEncuesta,
  Boolean problemasEconomicos,
  Boolean equipoComputo,
  Boolean consideroAbandonar,
  String metodosOProblemasAprendizaje,
  String fortalezasDebilidades,
  String tratamientoMedico,
  String consumoNicotina,
  String consumoAlcohol,
  String consumoOtrasSustancias,
  String estadoAnimo,
  String saludMental,
  String problemasViolencia,
  String statusFinalIncorporacion,
  String observacionesEncuesta
){
  public static AlumnoDetailResponse from(StudentDetailProjection p){
    ZoneId zone = ZoneId.of("America/Mexico_City"); // tu zona
    OffsetDateTime creado = p.getCreadoEn() == null ? null
        : OffsetDateTime.ofInstant(p.getCreadoEn(), zone);

   return new AlumnoDetailResponse(
      p.getAlumnoId(), p.getNombreCompleto(), p.getMatricula(), p.getActivo(), creado,
      p.getEstadoCivil(), p.getTurno(), p.getCarrera(),
      p.getMinutosDetalle(), p.getPromedioDetalle(), p.getTieneHijos(), p.getCuentaEquipoComputo(),
      p.getViveCon(), p.getApoyoFinanciero(), p.getSituacionActual(), p.getSituacionEstudiantil(),
      p.getObservacionesDetalle(),
      p.getMinutosEncuesta(), p.getPromedioEncuesta(), p.getProblemasEconomicos(), p.getEquipoComputo(),
      p.getConsideroAbandonar(), p.getMetodosOProblemasAprendizaje(), p.getFortalezasDebilidades(),
      p.getTratamientoMedico(), p.getConsumoNicotina(), p.getConsumoAlcohol(), p.getConsumoOtrasSustancias(),
      p.getEstadoAnimo(), p.getSaludMental(), p.getProblemasViolencia(), p.getStatusFinalIncorporacion(),
      p.getObservacionesEncuesta()
    );
  }
}
