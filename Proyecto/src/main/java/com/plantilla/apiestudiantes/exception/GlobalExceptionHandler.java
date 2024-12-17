package com.plantilla.apiestudiantes.exception;


import com.plantilla.apiestudiantes.dto.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de tipo {@link CursoNotFoundException}.
     * Esta excepción se lanza cuando no se encuentra un curso en el sistema.
     *
     * @param e La excepción lanzada cuando no se encuentra el curso.
     * @return Una respuesta HTTP con el código de estado 404 (NOT_FOUND) y el mensaje de error.
     */

    // Clase de excepción personalizada para Curso no encontrado
    @ExceptionHandler(CursoNotFoundException.class)
    public ResponseEntity<Response<String>> handleCursoNotFoundException(CursoNotFoundException e) {
        // Loguear la excepción para detalles de diagnóstico
        log.error("Curso no encontrado: " + e.getMessage(), e);

        // Respuesta personalizada para CursoNotFoundException
        Response<String> response = new Response<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }





    /**
     * Maneja las excepciones de tipo {@link CursoInvalidException}.
     * Esta excepción se lanza cuando los datos de un curso son inválidos.
     *
     * @param e La excepción lanzada cuando el curso es inválido.
     * @return Una respuesta HTTP con el código de estado 400 (BAD_REQUEST) y el mensaje de error.
     */
    // Clase de excepción personalizada para Curso inválido
    @ExceptionHandler(CursoInvalidException.class)
    public ResponseEntity<Response<String>> handleInvalidCursoException(CursoInvalidException e) {
        // Loguear la excepción para detalles de diagnóstico
        log.error("Curso inválido: " + e.getMessage(), e);

        // Respuesta personalizada para CursoInvalidException
        Response<String> response = new Response<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }





    /**
     * Maneja las excepciones de tipo {@link TemaException}.
     * Esta excepción se lanza cuando ocurre un error relacionado con el tema.
     *
     * @param e La excepción lanzada en relación con el tema.
     * @return Una respuesta HTTP con el código de estado 400 (BAD_REQUEST) y el mensaje de error.
     */
    // Clase de excepción personalizada para errores de Tema
    @ExceptionHandler(TemaException.class)
    public ResponseEntity<Response<String>> handleTemaException(TemaException e) {
        // Loguear la excepción para detalles de diagnóstico
        log.error("Error en el tema: " + e.getMessage(), e);

        Response<String> response = new Response<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }





    /**
     * Maneja las excepciones lanzadas por las validaciones fallidas de un objeto en el cuerpo de la solicitud
     * (por ejemplo, validaciones de @RequestBody).
     *
     * @param ex La excepción lanzada debido a la validación fallida de los argumentos.
     * @return Una respuesta HTTP con el código de estado 400 (BAD_REQUEST) y los detalles de los errores de validación.
     */
    // Manejo de exception por validaciones por @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.error("Error de validación: " + errors, ex);
        Response<Map<String, String>> response = new Response<>(false, "Errores de validación", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




    /**
     * Maneja las excepciones lanzadas por las violaciones de restricciones en los parámetros de la URL
     * (por ejemplo, validaciones de @PathVariable o @RequestParam).
     *
     * @param ex La excepción lanzada debido a la violación de restricciones en los parámetros de la URL.
     * @return Una respuesta HTTP con el código de estado 400 (BAD_REQUEST) y los detalles de los errores de validación.
     */
    // Manejo de exception por validaciones por @PathVariable y @RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Error de validación en parámetros de controller: " + errors, ex);
        Response<Map<String, String>> response = new Response<>(false, "Errores de validación en los parámetros", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    /**
     * Maneja las excepciones de tipo {@link DataBaseException}.
     * Esta excepción se lanza cuando ocurre un error específico relacionado con la persistencia en la base de datos.
     *
     * @param ex La excepción lanzada cuando ocurre un error en la base de datos.
     * @return Una respuesta HTTP con el código de estado 500 (INTERNAL_SERVER_ERROR) y el mensaje de error.
     */
    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<Response<String>> handleDataBaseException(DataBaseException ex) {
        // Loguear el error para diagnóstico
        log.error("Error al acceder a la base de datos: [ENTIDAD: {}] - [ID {}] -  [NOMBRE:{}] - [OPERACIÓN:{}] -  [CAUSA RAÍZ: {}] - [MENSAJE USUARIO: {}]",
                ex.getEntityType(),  ex.getEntityId(), ex.getEntityName(), ex.getOperation(), ex.getRootCause(), ex.getMessage());

        // Mensaje para el usuario final
        Response<String> response = new Response<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Maneja todas las excepciones no previstas en los controladores y servicios.
     * Esta excepción sirve como un manejo genérico de cualquier error no controlado previamente.
     *
     * @param e La excepción no controlada que se ha lanzado.
     * @return Una respuesta HTTP con el código de estado 500 (INTERNAL_SERVER_ERROR) y un mensaje genérico de error.
     */
    // Manejo de exception Generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleGeneralException(Exception e) {
        // Loguear la excepción para detalles de diagnóstico
        log.error("Error inesperado: " + e.getMessage(), e);

        // Respuesta genérica para cualquier excepción no capturada
        Response<String> response = new Response<>(false, "Ha ocurrido un error inesperado", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
