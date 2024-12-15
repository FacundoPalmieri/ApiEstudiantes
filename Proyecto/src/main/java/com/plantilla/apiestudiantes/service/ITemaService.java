package com.plantilla.apiestudiantes.service;

import com.plantilla.apiestudiantes.dto.Response;
import com.plantilla.apiestudiantes.dto.TemaDto;
import com.plantilla.apiestudiantes.model.Tema;

import java.util.List;
import java.util.Map;


public interface ITemaService {
    public Response <TemaDto> saveTema(Tema tema);

    public List<Tema> findAll();

    public Tema findById(Long id);

    // public Response<TemaDto> editTema(Long id, String nuevaModalidad);

}
