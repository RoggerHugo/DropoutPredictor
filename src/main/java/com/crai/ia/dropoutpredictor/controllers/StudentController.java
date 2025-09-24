package com.crai.ia.dropoutpredictor.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.crai.ia.dropoutpredictor.dto.AlumnoDetailResponse;
import com.crai.ia.dropoutpredictor.dto.AlumnoDetailWithPredictionResponse;
import com.crai.ia.dropoutpredictor.dto.AlumnoRequest;
import com.crai.ia.dropoutpredictor.dto.AlumnoResponse;
import com.crai.ia.dropoutpredictor.service.AlumnoService;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin(origins = {
    "https://dashboardpredictor.onrender.com",
    "http://localhost:4200"
}, allowedHeaders = {
    "Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"
}, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS }, exposedHeaders = { "Authorization" },
    maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {

  private final AlumnoService service;

  public StudentController(AlumnoService service) {
    this.service = service;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<AlumnoResponse> crear(@Valid @RequestBody AlumnoRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
  }

  @GetMapping
  public Page<AlumnoResponse> listar(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id,desc") String sort,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Long carreraId,
      @RequestParam(required = false) Long turnoId,
      @RequestParam(required = false) Long estadoCivilId,
      @RequestParam(required = false) Boolean activo) {
    String[] s = sort.split(",");
    Sort orden = Sort.by(new Sort.Order(Sort.Direction.fromString(s.length > 1 ? s[1] : "asc"), s[0]));
    Pageable pageable = PageRequest.of(page, size, orden);
    return service.listar(pageable, q, carreraId, turnoId, estadoCivilId, activo);
  }

  @GetMapping("/{id}")
  public AlumnoDetailResponse obtener(@PathVariable Long id) {
    return service.obtenerDetalle(id);
  }

  @GetMapping("/{id}/detail")
  public AlumnoDetailWithPredictionResponse obtenerDetalle(@PathVariable Long id) {
    return service.obtenerDetalleConPrediccion(id);
  }

  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  public AlumnoResponse actualizar(
      @PathVariable Long id,
      @Valid @RequestBody AlumnoRequest req) {
    return service.actualizar(id, req);
  }

  @PatchMapping("/{id}/activo")
  public AlumnoResponse toggleActivo(@PathVariable Long id, @RequestParam boolean value) {
    AlumnoRequest base = service.obtener(id) != null
        ? new AlumnoRequest(
            service.obtener(id).nombreCompleto(),
            service.obtener(id).matricula(),
            service.obtener(id).fechaNacimiento(),
            service.obtener(id).edad(),
            service.obtener(id).estadoCivilId(),
            service.obtener(id).turnoId(),
            service.obtener(id).carreraId(),
            value)
        : null;
    return service.actualizar(id, base);
  }

  /*
   * @DeleteMapping("/{id}")
   * 
   * @ResponseStatus(HttpStatus.NO_CONTENT)
   * public void eliminar(@PathVariable Long id, @RequestParam(defaultValue =
   * "true") boolean soft) {
   * service.eliminar(id, soft);
   * }
   */
}
