package com.crai.ia.dropoutpredictor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.crai.ia.dropoutpredictor.entity.Alumno;
import com.crai.ia.dropoutpredictor.entity.StudentDetailProjection;

public interface AlumnoRepository extends JpaRepository<Alumno, Long>, JpaSpecificationExecutor<Alumno> {
  boolean existsByMatricula(String matricula);

  @Query(value = """
  SELECT a.id AS alumno_id, a.nombre_completo, a.matricula, a.activo, a.creado_en, ec.nombre AS estado_civil,
  t.descripcion AS turno, ca.nombre AS carrera, d.minutos_traslado AS minutos_detalle, d.promedio AS promedio_detalle,
  d.tiene_hijos, d.cuenta_equipo_computo, vc.nombre AS vive_con, af.nombre AS apoyo_financiero, sa.nombre AS situacion_actual,
  se.nombre AS situacion_estudiantil, d.observaciones AS observaciones_detalle, e.minutos_traslado AS minutos_encuesta,
  e.promedio AS promedio_encuesta, e.problemas_economicos, e.equipo_computo, e.considero_abandonar, e.metodos_o_problemas_aprendizaje,
  e.fortalezas_debilidades, e.tratamiento_medico, e.consumo_nicotina, e.consumo_alcohol, e.consumo_otras_sustancias,
  e.estado_animo, e.salud_mental, e.problemas_violencia, e.status_final_incorporacion, e.observaciones AS observaciones_encuesta
	FROM alumnos a 
		INNER JOIN detalle_estudiantes d  ON d.alumno_id = a.id
		INNER JOIN encuesta_entrevista e  ON e.alumno_id = a.id
		INNER JOIN c_estado_civil ec ON ec.id = a.estado_civil_id
		INNER JOIN c_carrera ca ON ca.id = a.carrera_id
		INNER JOIN c_turno t  ON t.id = a.turno_id
		INNER JOIN c_vive_con vc ON vc.id = d.vive_con_id
		INNER JOIN c_apoyo_financiero af ON af.id = d.apoyo_financiero_id
		INNER JOIN c_situacion_actual sa ON sa.id = d.situacion_actual_id
		INNER JOIN c_situacion_estudiantil se ON se.id = d.situacion_estudiantil_id
			WHERE a.id = :id
			""", nativeQuery = true)
  Optional<StudentDetailProjection> findDetailById(@Param("id") Long id);

}