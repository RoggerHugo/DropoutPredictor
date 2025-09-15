package com.crai.ia.dropoutpredictor.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AlumnoRequest(
        @NotBlank @Size(max = 200) String nombreCompleto,
        @Size(max = 30) String matricula, 
        LocalDate fechaNacimiento, 
        @Min(0) @Max(120) Integer edad, 
        @NotNull Long estadoCivilId,
        Long turnoId, 
        @NotNull Long carreraId,
        @NotNull Boolean activo) {
}