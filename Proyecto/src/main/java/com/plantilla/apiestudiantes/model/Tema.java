package com.plantilla.apiestudiantes.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(columnDefinition = "VARCHAR(20)", length = 20,nullable = false, unique = true)
    @NotBlank
    String nombre;


    @Column(columnDefinition = "VARCHAR(100)", length = 100,nullable = true)
    String descripcion;

    // Relación ManyToOne: Un Tema pertenece a un Curso
    @ManyToOne(targetEntity = Curso.class)
    @JoinColumn(name = "curso_id", nullable = true) // La columna que va a almacenar la clave foránea del curso
    private Curso curso;


    @CreationTimestamp
    // Asigna la fecha y hora de creación de manera automática cuando el registro se inserta en la base de datos.
    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp // Asigna automáticamente la fecha de la última modificación cada vez que el registro se actualiza.
    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaUltimaModificacion;
}
