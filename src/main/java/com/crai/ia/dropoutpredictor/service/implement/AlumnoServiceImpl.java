package com.crai.ia.dropoutpredictor.service.implement;

import com.crai.ia.dropoutpredictor.dto.*;
import com.crai.ia.dropoutpredictor.entity.*;
import com.crai.ia.dropoutpredictor.integration.crai.CraiClient;
import com.crai.ia.dropoutpredictor.repository.AlumnoRepository;
import com.crai.ia.dropoutpredictor.repository.CarreraRepository;
import com.crai.ia.dropoutpredictor.repository.EstadoCivilRepository;
import com.crai.ia.dropoutpredictor.repository.TurnoRepository;
import com.crai.ia.dropoutpredictor.service.AlumnoService;
import com.crai.ia.dropoutpredictor.config.NotFoundException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

import static com.crai.ia.dropoutpredictor.entity.AlumnoSpecs.*;

@Service
@Transactional
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repo;
    private final EstadoCivilRepository estadoCivilRepo;
    private final TurnoRepository turnoRepo;
    private final CarreraRepository carreraRepo;
    private final CraiClient crai;

    public AlumnoServiceImpl(AlumnoRepository repo,
            EstadoCivilRepository estadoCivilRepo,
            TurnoRepository turnoRepo,
            CarreraRepository carreraRepo,
            CraiClient crai) {
        this.repo = repo;
        this.estadoCivilRepo = estadoCivilRepo;
        this.turnoRepo = turnoRepo;
        this.carreraRepo = carreraRepo;
        this.crai = crai;
    }

    private AlumnoResponse toRes(Alumno a) {
        return new AlumnoResponse(
                a.getId(), a.getNombreCompleto(), a.getMatricula(),
                a.getFechaNacimiento(), a.getEdad(),
                a.getEstadoCivil() != null ? a.getEstadoCivil().getId() : null,
                a.getEstadoCivil() != null ? a.getEstadoCivil().getNombre() : null,
                a.getTurno() != null ? a.getTurno().getId() : null,
                a.getTurno() != null ? a.getTurno().getDescripcion() : null,
                a.getCarrera() != null ? a.getCarrera().getId() : null,
                a.getCarrera() != null ? a.getCarrera().getNombre() : null,
                a.getActivo(), a.getCreadoEn());
    }

    private void apply(Alumno a, AlumnoRequest r) {
        a.setNombreCompleto(r.nombreCompleto());
        a.setMatricula(r.matricula());
        a.setFechaNacimiento(r.fechaNacimiento());
        if (r.edad() != null)
            a.setEdad(r.edad());
        else if (r.fechaNacimiento() != null)
            a.setEdad(Period.between(r.fechaNacimiento(), LocalDate.now()).getYears());
        a.setActivo(r.activo());

        EstadoCivil ec = estadoCivilRepo.findById(r.estadoCivilId())
                .orElseThrow(() -> new NotFoundException("EstadoCivil " + r.estadoCivilId() + " no existe"));
        a.setEstadoCivil(ec);

        Carrera ca = carreraRepo.findById(r.carreraId())
                .orElseThrow(() -> new NotFoundException("Carrera " + r.carreraId() + " no existe"));
        a.setCarrera(ca);

        if (r.turnoId() != null) {
            Turno t = turnoRepo.findById(r.turnoId())
                    .orElseThrow(() -> new NotFoundException("Turno " + r.turnoId() + " no existe"));
            a.setTurno(t);
        } else {
            a.setTurno(null);
        }
    }

    @Override
    public AlumnoResponse crear(AlumnoRequest req) {
        Alumno a = new Alumno();
        apply(a, req);
        return toRes(repo.save(a));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlumnoResponse> listar(Pageable pageable, String q, Long carreraId, Long turnoId, Long estadoCivilId,
            Boolean activo) {
        Specification<Alumno> spec = Specification.allOf(
                q(q),
                porCarrera(carreraId), 
                porTurno(turnoId), 
                porEstadoCivil(estadoCivilId),
                porActivo(activo));

        return repo.findAll(spec, pageable).map(this::toRes);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtener(Long id) {
        Alumno a = repo.findById(id).orElseThrow(() -> new NotFoundException("Alumno " + id + " no existe"));
        return toRes(a);
    }

    @Override
    public AlumnoResponse actualizar(Long id, AlumnoRequest req) {
        Alumno a = repo.findById(id).orElseThrow(() -> new NotFoundException("Alumno " + id + " no existe"));
        apply(a, req);
        return toRes(repo.save(a));
    }

    @Override
    public void eliminar(Long id, boolean soft) {
        Alumno a = repo.findById(id).orElseThrow(() -> new NotFoundException("Alumno " + id + " no existe"));
        if (soft) {
            a.setActivo(Boolean.FALSE);
            repo.save(a);
        } else {
            repo.delete(a);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoDetailResponse obtenerDetalle(Long id) {
        var p = repo.findDetailById(id)
                .orElseThrow(() -> new NotFoundException("Alumno " + id + " no existe o no tiene detalle/encuesta"));
        return AlumnoDetailResponse.from(p);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoDetailWithPredictionResponse obtenerDetalleConPrediccion(Long id) {
        var p = repo.findDetailById(id)
                .orElseThrow(() -> new NotFoundException("Alumno " + id + " no existe o no tiene detalle/encuesta"));

        var detail = AlumnoDetailResponse.from(p);

        var features = FeatureMapper.toFeatures(detail);
        var prediction = crai.predict(new PredictRequest(features));
        System.out.println("Predicción recibida: " + prediction);
        return new AlumnoDetailWithPredictionResponse(detail, prediction);
    }
}
