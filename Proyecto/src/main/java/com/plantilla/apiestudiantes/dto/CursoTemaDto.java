package com.plantilla.apiestudiantes.dto;


import com.plantilla.apiestudiantes.model.Tema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CursoTemaDto {

    @NotBlank(message = "El nombre del curso no puede estar vacío -  DTO.")
    private String nombre;


    @NotEmpty(message = "El curso debe poseer temas - DTO.")
    private List<String> listaDeTemas;
}
