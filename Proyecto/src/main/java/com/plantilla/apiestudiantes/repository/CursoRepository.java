package com.plantilla.apiestudiantes.repository;

import com.plantilla.apiestudiantes.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNombreIgnoreCase(String nombre);


    @Query("SELECT c.nombre FROM Curso c WHERE c.id = :id")
    String findNombreCursoById(@Param("id") Long id);



}
