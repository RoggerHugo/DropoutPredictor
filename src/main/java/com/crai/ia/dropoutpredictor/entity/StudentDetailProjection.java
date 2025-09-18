package com.crai.ia.dropoutpredictor.entity;

import java.math.BigDecimal;
import java.time.Instant;

public interface StudentDetailProjection {
    Long getAlumnoId();

    String getNombreCompleto();

    String getMatricula();

    Boolean getActivo();

    Instant getCreadoEn();

    String getEstadoCivil();

    String getTurno();

    String getCarrera();

    Integer getMinutosDetalle();

    BigDecimal getPromedioDetalle();

    Boolean getTieneHijos();

    Boolean getCuentaEquipoComputo();

    String getViveCon();

    String getApoyoFinanciero();

    String getSituacionActual();

    String getSituacionEstudiantil();

    String getObservacionesDetalle();

    Integer getMinutosEncuesta();

    BigDecimal getPromedioEncuesta();

    Boolean getProblemasEconomicos();

    Boolean getEquipoComputo();

    Boolean getConsideroAbandonar();

    String getMetodosOProblemasAprendizaje();

    String getFortalezasDebilidades();

    String getTratamientoMedico();

    String getConsumoNicotina();

    String getConsumoAlcohol();

    String getConsumoOtrasSustancias();

    String getEstadoAnimo();

    String getSaludMental();

    String getProblemasViolencia();

    String getStatusFinalIncorporacion();

    String getObservacionesEncuesta();
}