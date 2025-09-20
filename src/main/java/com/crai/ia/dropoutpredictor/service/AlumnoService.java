package com.crai.ia.dropoutpredictor.service;

import com.crai.ia.dropoutpredictor.dto.*;
import org.springframework.data.domain.*;

public interface AlumnoService {
    AlumnoResponse crear(AlumnoRequest req);

    Page<AlumnoResponse> listar(Pageable pageable, String q, Long carreraId, Long turnoId, Long estadoCivilId,
            Boolean activo);

    AlumnoResponse obtener(Long id);

    AlumnoResponse actualizar(Long id, AlumnoRequest req);

    void eliminar(Long id, boolean soft);

    AlumnoDetailResponse obtenerDetalle(Long id);

    AlumnoDetailWithPredictionResponse obtenerDetalleConPrediccion(Long id);
}