package com.plantilla.apiestudiantes.service;

import com.plantilla.apiestudiantes.dto.CursoDto;
import com.plantilla.apiestudiantes.dto.Response;
import com.plantilla.apiestudiantes.model.Curso;
import org.springframework.data.domain.Page;

public interface ICursoService {

    public Response<CursoDto> saveCurso(Curso curso);

    public Response<Page<CursoDto>> getCursos(int page, int size);

    public Response<CursoDto> getCurso(Long id);

    public Response<CursoDto> editCurso(CursoDto cursoDto);

    public Response<CursoDto> editCurso(Long id, String nuevaModalidad);


}
