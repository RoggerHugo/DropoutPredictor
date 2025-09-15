package com.crai.ia.dropoutpredictor.entity;

import org.springframework.data.jpa.domain.Specification;

public class AlumnoSpecs {

    public static Specification<Alumno> q(String q) {
        if (q == null || q.isBlank())
            return null; // << importante para allOf
        String like = "%" + q.toLowerCase() + "%";
        return (root, cq, cb) -> cb.or(
                cb.like(cb.lower(root.get("nombreCompleto")), like),
                cb.like(cb.lower(root.get("matricula")), like));
    }

    public static Specification<Alumno> porCarrera(Long carreraId) {
        if (carreraId == null)
            return null;
        return (root, cq, cb) -> cb.equal(root.join("carrera").get("id"), carreraId);
    }

    public static Specification<Alumno> porTurno(Long turnoId) {
        if (turnoId == null)
            return null;
        return (root, cq, cb) -> cb.equal(root.join("turno").get("id"), turnoId);
    }

    public static Specification<Alumno> porEstadoCivil(Long estadoCivilId) {
        if (estadoCivilId == null)
            return null;
        return (root, cq, cb) -> cb.equal(root.join("estadoCivil").get("id"), estadoCivilId);
    }

    public static Specification<Alumno> porActivo(Boolean activo) {
        if (activo == null)
            return null;
        return (root, cq, cb) -> cb.equal(root.get("activo"), activo);
    }
}
