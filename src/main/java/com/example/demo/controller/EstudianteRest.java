package com.example.demo.controller;

import com.example.demo.entity.Estudiante;
import com.example.demo.service.EstudianteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/estudiantes")
public class EstudianteRest {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> listAllEstudiantes() {
        log.info("Fetching all the students");
        List<Estudiante> estudiantes =  new ArrayList<>();
        estudiantes = estudianteService.findEstudianteAll();
            if (estudiantes.isEmpty()) {
                return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(estudiantes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable("id") Long id) {
        log.info("Fetching student with id {}", id);
        Estudiante estudiante = estudianteService.getEstudiante(id);
        if (estudiante == null) {
            log.error("Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(estudiante);
    }

    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante( @RequestBody Estudiante estudiante, BindingResult result) {
        log.info("Creating student : {}", estudiante);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Estudiante estudianteDB = estudianteService.createEstudiante (estudiante);

        return  ResponseEntity.status( HttpStatus.CREATED).body(estudianteDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEstudiante(@PathVariable("id") long id, @RequestBody Estudiante estudiante) {
        log.info("Updating student with id {}", id);

        Estudiante currentStudent = estudianteService.getEstudiante(id);

        if ( currentStudent == null ) {
            log.error("Unable to update. Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        estudiante.setId(id);
        currentStudent=estudianteService.updateEstudiante(estudiante);
        return  ResponseEntity.ok(currentStudent);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Estudiante> deleteEstudiante(@PathVariable("id") long id) {
        log.info("Deleting student with id {}", id);

        Estudiante estudiante = estudianteService.getEstudiante(id);
        boolean deleted = estudianteService.deleteEstudiante(estudiante);

        if(!deleted) {
            log.error("Unable to delete. Student with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
