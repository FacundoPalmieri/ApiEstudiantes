package com.plantilla.apiestudiantes.repository;

import com.plantilla.apiestudiantes.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    List<Tema> findByCurso_Id(Long cursoId);

    Boolean existsByNombre(String name);

    @Query("SELECT t.nombre FROM Tema t WHERE t.curso.id = :cursoId")
    List<String> findNameTemasByCursoId(@Param("cursoId")Long cursoId);

}
