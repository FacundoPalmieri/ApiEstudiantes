package com.plantilla.apiestudiantes.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Builder
public record CursoDto(

        @Null (message = "El id debe ser nulo - DTO")
        Long id,

        @NotBlank(message = "El nombre del curso no puede estar vacío -  DTO.")
        String nombre,

        @NotBlank(message = "La modalidad no puede estar vacía - DTO.")
        String modalidad,

        @NonNull
        @Future
        Date fecha_finalizacion,

        List<Long>listaTemasId



) {
}
