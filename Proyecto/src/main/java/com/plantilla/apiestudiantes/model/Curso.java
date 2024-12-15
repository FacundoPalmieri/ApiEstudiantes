package com.plantilla.apiestudiantes.model;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", length = 20 , nullable = false, unique = true)
    @NotBlank
    private String nombre;


    @Column(columnDefinition = "VARCHAR(20)",length = 20, nullable = false)
    @NotBlank
    private String modalidad;

    @Column(columnDefinition = "DATE", nullable = false)
    @NonNull
    private Date fecha_finalizacion;


    @OneToMany(targetEntity = Tema.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "curso") // Esto indica que la relación se maneja desde el campo "curso" en la entidad Tema
    private List<Tema> listaDeTemas;

    @Column(columnDefinition = "BOOL", nullable = false)
    private Boolean habilitado;


    @CreationTimestamp  // Asigna la fecha y hora de creación de manera automática cuando el registro se inserta en la base de datos.
    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp // Asigna automáticamente la fecha de la última modificación cada vez que el registro se actualiza.
    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaUltimaModificacion;
}

