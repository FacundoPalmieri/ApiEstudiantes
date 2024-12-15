package com.plantilla.apiestudiantes.controller;

import com.plantilla.apiestudiantes.dto.Response;
import com.plantilla.apiestudiantes.dto.TemaDto;
import com.plantilla.apiestudiantes.exception.TemaException;
import com.plantilla.apiestudiantes.model.Tema;
import com.plantilla.apiestudiantes.service.ITemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TemaController {
    @Autowired
    private ITemaService ItemaService;

    @PostMapping ("/creartema")
    public ResponseEntity<Response<TemaDto>>CrearTema(@RequestBody Tema tema) {

          Response <TemaDto> response = ItemaService.saveTema(tema);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping ("/consultar/temas")
    public List<Tema> ConsultarTemas() {
        return ItemaService.findAll();
    }

    @GetMapping ("/consultar/tema/{id}")
    public Tema consultarTema(@PathVariable long id) {
       return ItemaService.findById(id);

    }

    /*
    @PatchMapping ("/editar/tema/{id}")
    public ResponseEntity<Response<TemaDto>> editarCurso(@PathVariable long id,
                                         @RequestParam (name = "descripcion") String nuevaDescripcion){

        Response <TemaDto> response  = ItemaService.editTema(id, nuevaDescripcion);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
    }

     */


}

