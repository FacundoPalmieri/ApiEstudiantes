package com.plantilla.apiestudiantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plantilla.apiestudiantes.model.Curso;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TemaDto(

        Long id_Tema,

        @NotBlank(message = "El nombre del TEMA no puede estar vacío DTO.")
        String nombre,

        String descripcion,

        Long idCurso

) {
}
