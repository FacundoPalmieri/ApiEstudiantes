package com.plantilla.apiestudiantes.controller;


import com.plantilla.apiestudiantes.dto.CursoDto;
import com.plantilla.apiestudiantes.dto.CursoTemaDto;
import com.plantilla.apiestudiantes.dto.Response;
import com.plantilla.apiestudiantes.model.Curso;
import com.plantilla.apiestudiantes.service.ICursoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CursoController {
    @Autowired
    private ICursoService IcursoService;

    /**
     * Crea un nuevo curso en el sistema.
     *
     * Este método recibe los datos de un curso  y delega en el servicio
     * para realizar las validaciones y el guardado del curso. La respuesta contiene
     * el estado de la operación y el curso guardado.
     *
     *
     * @param curso El objeto con los datos del curso a guardar.
     * @return Un objeto {@link Response} que contiene el resultado de la operación y el curso guardado.
     */

    @PostMapping ("/curso/crear")
    public ResponseEntity<Response<CursoDto>> crearCurso(@RequestBody @Valid  Curso curso) {

        // Llama al servicio y devuelve la respuesta
        Response<CursoDto> response = IcursoService.saveCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Devuelve 201 creado

    }


    /**
     * Obtiene una lista de cursos desde el servicio de manera paginada.
     *
     * Este método permite recuperar los cursos en forma de una página, donde el número
     * de la página y el tamaño de la página se definen a través de los parámetros
     * de la solicitud.
     *
     * @param page El número de páginas que desea recuperar(comienza de 0)
     * @param size El tamaño de la página, es decir, el número máximo de registros por página.
     * @return Un objeto {@link Page} que contiene la lista de cursos correspondientes a la página solicitada.
     */
    @GetMapping ("/cursos/listar")
    public ResponseEntity<Response<Page<CursoDto>>> listarCursos(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(0) @RequestParam(defaultValue = "10") int size) {

        Response<Page<CursoDto>>response = IcursoService.getCursos(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(response); // Devuelve 200 OK

    }



    /**
     * Obtiene un curso desde el servicio de acuerdo al parametro que recibe por URL
     *
     * Este método permite recuperar un curso determinado, incluyendo su nombre y lista de temas,
     * a partir del ID que se pasa como parámetro en la URL
     *
     * @param id El identificador único del curso que se desea recuperar.
     * @return un objeto {@link CursoTemaDto} que contiene Nombre y Lista de Temas
     */

    @GetMapping ("/curso/mostrar/{id}")
    @ResponseBody
    public ResponseEntity<Response<CursoDto>> ObtenerCurso(@PathVariable @NonNull Long id) {

        Response<CursoDto> response =  IcursoService.getCurso(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




    @PatchMapping("curso/modificar/{id}")
    public ResponseEntity<Response<CursoDto>> modificarCurso(@PathVariable @NonNull Long id,
                                                                   @RequestParam(name = "modalidad")
                                                                   @NotBlank( message = "La modalidad no puede estar en blanco") String NuevaModalidad){

        Response<CursoDto> response= IcursoService.editCurso(id, NuevaModalidad);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //Actuliza el objeto completo, sino recibe todos los valores los pone en NULL
    @PutMapping  ("/curso/modificar")
    public ResponseEntity<Response<CursoDto>> modificarCurso(@RequestBody @Valid CursoDto cursoDto) {

        Response<CursoDto> response = IcursoService.editCurso(cursoDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }





}




