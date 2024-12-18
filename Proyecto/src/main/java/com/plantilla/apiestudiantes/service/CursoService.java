package com.plantilla.apiestudiantes.service;

import com.plantilla.apiestudiantes.dto.*;
import com.plantilla.apiestudiantes.exception.CursoNotFoundException;
import com.plantilla.apiestudiantes.exception.CursoInvalidException;
import com.plantilla.apiestudiantes.exception.DataBaseException;
import com.plantilla.apiestudiantes.model.Curso;
import com.plantilla.apiestudiantes.model.Tema;
import com.plantilla.apiestudiantes.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Servicio para gestionar la lógica de negocio relacionada con los cursos.

 * Esta clase maneja la creación, lectura, actualización y eliminación de cursos,
 * además de realizar las validaciones necesarias antes de interactuar con la base de datos.
 * Incluye la validación de la existencia de un curso por ID o nombre, y la verificación de la modalidad
 * del curso antes de realizar la operación correspondiente.
 *
 * @author  Facundo Palmieri
 * @version 1.0
 * @since 2024-12-11
 */


@Service
public class CursoService implements ICursoService {
    @Autowired
    private CursoRepository cursoRepository;


    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;


    /**
     * Guarda un nuevo curso en el sistema después de realizar las validaciones necesarias

     * Este método realiza las siguientes validaciones antes de guardar el curso
     * -    Verifica que el nombre del curso no esté ya registrado.
     *      Valida que la modalidad del curso sea "Presencial" o "Virtual".

     *
     * @param curso  El objeto con los datos del curso a guardar.
     * @return  Un objeto {@link Response} que contiene el resultado de la operación y el curso guardado.
     * @throws CursoInvalidException Si alguna de las validaciones falla.
     */
    @Override
    public Response<CursoDto> saveCurso(Curso curso) {

        try{
            // Validaciones previas.
            validateNameNotExist(curso.getNombre());
            validateModality(curso.getModalidad());

            // Guarda el curso
            Curso cursoAux = cursoRepository.save(curso);

            // Construye el DTO para devolver
            CursoDto cursoDto = buildCursoDtoCreate(cursoAux);

            //Mensaje de éxito
            String successMessage = messageSource.getMessage(
                    "curso.save.success",
                    new Object[]{curso.getNombre()},
                    LocaleContextHolder.getLocale());

            return new Response<>(true, successMessage,cursoDto);

        }catch (DataAccessException ex) {
            // Captura problemas con la base de datos
            String userMessage = messageSource.getMessage(
                    "curso.save.error",
                    new Object[]{curso.getNombre()},
                    LocaleContextHolder.getLocale());

            //Se guarda el motivo de la causa raíz
            String rootCause = ex.getCause() != null ? ex.getCause().toString() : "Desconocida";

            throw new DataBaseException(userMessage,"Curso", curso.getId(), curso.getNombre(), "Save", rootCause);
        }
    }





    /**
     * Obtiene los cursos de manera paginada desde el repositorio

     * Este método construye un objeto {@link Pageable} con los parámetros de la página
     * y el tamaño de la página proporcionados, y luego recupera los cursos correspondientes
     * desde el repositorio.
     *
     * @param page número de páginas que desea recuperar(comienza de 0)
     * @param size  El tamaño de la página, es decir, el número máximo de registros por página.
     * @return Un objeto {@link Page} que contiene la lista de cursos correspondientes a la página solicitada.
     */

    @Override
    public Response<Page<CursoDto>> getCursos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        //Recupera el listado paginado.
        Page<Curso> cursos = cursoRepository.findAll(pageable);

        //Mapea Curso a CursoDTO.
        Page<CursoDto> cursosDto = buildCursoDtoPageable(cursos);

        String userMessage = messageSource.getMessage("curso.getAll.success", null, LocaleContextHolder.getLocale());

        return new Response<>(true, userMessage,cursosDto);
    }






    @Override
    public Response<CursoDto> getCurso(Long id) {

        //Valída y recupera el curso.
        Curso curso = findByIdCurso(id);

        //Se construye un DTO.
        CursoDto cursoDto= buildCursoDto(curso);

        String userMessage = messageSource.getMessage("curso.get.success", new Object[]{curso.getNombre()}, LocaleContextHolder.getLocale());

        return new Response<>(true, userMessage, cursoDto);
    }






    @Override
    public Response<CursoDto> editCurso(Long id, String nuevaModalidad) {

        //Valída y recupera Curso.

        String validationMessage = messageSource.getMessage("curso.validate.id", null, LocaleContextHolder.getLocale());
        Curso cursoModificado = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException(validationMessage));

        // Actualiza atributo
        cursoModificado.setModalidad(nuevaModalidad);

        //Guarda en BD
        cursoRepository.save(cursoModificado);

        String userMessage = messageSource.getMessage("curso.update.success", new Object[]{cursoModificado.getNombre()}, LocaleContextHolder.getLocale());
        return new Response<>(true, userMessage, buildCursoDto(cursoModificado));

    }





    @Override
    public Response<CursoDto> editCurso(CursoDto cursoDto) {

        //Valída y Recupera el curso
        String validationMessage = messageSource.getMessage("curso.validate.id", null, LocaleContextHolder.getLocale());
        Curso curso = cursoRepository.findById(cursoDto.id()).orElseThrow(() -> new CursoNotFoundException(validationMessage));

        //Actualiza el curso
        // Actualiza el curso con los datos del DTO
        curso.setNombre(cursoDto.nombre());
        curso.setModalidad(cursoDto.modalidad());
        curso.setFecha_finalizacion(cursoDto.fecha_finalizacion());

        //Guarda en BD
         Curso cursoModificado = cursoRepository.save(curso);

        //Construye Respuesta
        CursoDto cursoDtoRta = buildCursoDto(cursoModificado);

        String userMessage = messageSource.getMessage("curso.update.success", new Object[]{cursoModificado.getNombre()}, LocaleContextHolder.getLocale());
        return new Response<>(true, userMessage, cursoDtoRta);

    }





    // Métodos protected

    protected Curso findByIdCurso(Long cursoId) {
        if(cursoId == null) {
            throw new CursoInvalidException("curso.validate.modality.empty");
        }

        String userMessage = messageSource.getMessage("curso.validate.id", null, LocaleContextHolder.getLocale());

        return cursoRepository.findById(cursoId).orElseThrow(() -> new CursoNotFoundException(userMessage));

    }






    // Métodos privados

    private void validateNameNotExist (String name) {
        if (cursoRepository.findByNombreIgnoreCase(name).isPresent()) {
            // Obtiene el mensaje desde el archivo de propiedades
            String userMessage = messageSource.getMessage(
                    "curso.validate.name",  // Clave del mensaje
                    new Object[]{name},          // Argumento para reemplazar en el mensaje
                    LocaleContextHolder.getLocale()); // Localización actual


            throw new CursoInvalidException(userMessage);
        }

    }

    private void validateModality (String modality) {

        if(modality == null || modality.isEmpty()){
            throw new CursoInvalidException("curso.validate.modality.empty");
        }

        if(!(modality.equalsIgnoreCase("Presencial") || modality.equalsIgnoreCase("Virtual"))){
            String userMessage = messageSource.getMessage(
                    "curso.validate.modality.error",
                    new Object[]{modality},
                    LocaleContextHolder.getLocale()
            );
            throw new CursoInvalidException(userMessage);
        }

    }


    private CursoDto buildCursoDtoCreate (Curso curso) {

        return CursoDto.builder()
                .id(curso.getId()) //Método generado automáticamente en el record
                .nombre(curso.getNombre())
                .modalidad(curso.getModalidad())
                .fecha_finalizacion(curso.getFecha_finalizacion())
                .build();
    }


    private CursoDto buildCursoDto (Curso curso) {

        return CursoDto.builder()
                .id(curso.getId()) // Método generado automáticamente en el record
                .nombre(curso.getNombre())
                .modalidad(curso.getModalidad())
                .fecha_finalizacion(curso.getFecha_finalizacion())
                .listaTemasId(curso.getListaDeTemas().stream()
                        .map(Tema::getId)
                        .toList())
                .build();
    }




    private Page<CursoDto>  buildCursoDtoPageable (Page<Curso> cursos) {

        return cursos.map(curso -> CursoDto.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .modalidad(curso.getModalidad())
                .fecha_finalizacion(curso.getFecha_finalizacion())
                .listaTemasId(curso.getListaDeTemas().stream() // Que Tipo de dato tiene la lista? RTA = TEMA
                        .map(Tema::getId) // De ese tema, obtengo el ID
                        .toList())
                .build());
    }







/*
    private CursoDto buildCursoDto (Curso curso, boolean isCreate) {

        CursoDto.CursoDtoBuilder builder = CursoDto.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .modalidad(curso.getModalidad())
                .fecha_finalizacion(curso.getFecha_finalizacion());

        if (!isCreate) { // Solo agregas la lista de temas si no es una creación
            builder.listaTemasId(curso.getListaDeTemas().stream()
                    .map(Tema::getId)
                    .toList());
        }
        return builder.build();

    }

 */

}
