package com.plantilla.apiestudiantes.service;

import com.plantilla.apiestudiantes.dto.Response;
import com.plantilla.apiestudiantes.dto.TemaDto;
import com.plantilla.apiestudiantes.exception.CursoInvalidException;
import com.plantilla.apiestudiantes.exception.TemaException;
import com.plantilla.apiestudiantes.model.Tema;
import com.plantilla.apiestudiantes.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TemaService implements ITemaService {

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private CursoService cursoService;


    @Override
    public Response<TemaDto> saveTema(Tema tema) {

        // Validaciones previas.
        validNoEmptyTema(tema);
        validateNameNotExist(tema.getNombre());

        //Guarda el tema
        Tema temaAux = temaRepository.save(tema);

        // Construye el DTO para devolver
        TemaDto temaDto = buildTemaDto(temaAux);

        return new Response<>(true, "Se ha guardado correctamente",temaDto);

    }

    private void validNoEmptyTema(Tema tema) {
        // Verificar si el curso es nulo
        if (tema == null) {
            throw new CursoInvalidException("El tema no puede ser nulo.");
        }

        // Verificar que la descripción no esté vacía
        if (tema.getDescripcion() == null || tema.getDescripcion().trim().isEmpty()) {
            throw new CursoInvalidException("El nombre del tema no puede estar vacío.");
        }

    }

    private void validateNameNotExist(String name){

        if(temaRepository.existsByNombre(name)){
            throw new TemaException("El nombre del tema YA existe");
        }
    }

    private TemaDto buildTemaDto(Tema tema) {

        return TemaDto.builder()
                .id_Tema(tema.getId())
                .nombre(tema.getNombre())
                .descripcion(tema.getDescripcion())
                .idCurso(tema.getCurso().getId())

                .build();
    }



    @Override
    public List<Tema> findAll() {
        return temaRepository.findAll();
    }

    @Override
    public Tema findById(Long id) {
        return temaRepository.findById(id).orElse(null);
    }

/*
    @Override
    public Response<TemaDto> editTema(Long id, String nuevaDescripcion) {

        //Valida que exista el Tema
        validExist(id);

        //Recupera el tema
        Tema temaAux = temaRepository.findById(id).orElse(null);

        //Actualiza el tema
        temaAux.setDescripcion(nuevaDescripcion);

        //Guarda el tema.
        temaRepository.save(temaAux);

        // Mapeo a DTO.
        TemaDto temaDto = convertToDto(temaAux);

        return new Response<>(true, "Se ha actualizado correctamente",temaDto);

    }

 */

    private void validExist (Long id){

        if(id == null){
            throw new TemaException("El ID no puede ser nulo");
        }

        if(!temaRepository.existsById(id)){
            throw new TemaException("El ID no existe");
        }
    }




    protected List<Tema> findAllById(List<Long> ids) {
        return temaRepository.findAllById(ids);
    }

    protected List<Tema> findByCursoId(Long cursoId) {

        return temaRepository.findByCurso_Id(cursoId);

    }

    protected List<String> findNameByCursoId(Long cursoId) {

        return temaRepository.findNameTemasByCursoId(cursoId);

    }

    protected List<Long> extractIdsFromDto(List<TemaDto> listaTemasDto) {
        List<Long> listaIds = listaTemasDto.stream()
                .map(TemaDto::id_Tema)
                .toList();

        return listaIds;
    }

    /*
    protected TemaDto convertToDto(Tema tema) {

        return new TemaDto(tema.getId(), tema.getNombre(), tema.getDescripcion(), tema.getCurso());


    }



     */



        /*
    protected List<Tema> convertFromDto (List<TemaDto> listaTemaDto) {
        List<Tema> listaTemas = listaTemaDto.stream()
                .map(temaDto -> new Tema(temaDto.id_Tema(), temaDto.nombre(), temaDto.descripcion()))
                .toList();

        return listaTemas;
    }
    */





}
