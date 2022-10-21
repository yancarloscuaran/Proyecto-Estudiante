package com.example.demo.controller;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Estudiante;
import com.example.demo.service.CursoService;
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
@RequestMapping("/cursos")
public class CursoRest {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listAllCursos() {
        log.info("Fetching all the courses");
        List<Curso> cursos =  new ArrayList<>();
        cursos = cursoService.findCursoAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(cursos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable("id") Long id) {
        log.info("Fetching course with id {}", id);
        Curso curso = cursoService.getCurso(id);
        if (curso == null) {
            log.error("Course with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(curso);
    }

    @GetMapping("/estudiantes-by-curso")
    public ResponseEntity<List<Estudiante>> getEstudianteByCurso(@RequestParam(name = "cursoId") Long cursoId ) {
        List<Estudiante> estudiantesByCurso =  new ArrayList<>();

        Curso cursoDB = cursoService.getCurso(cursoId);

        if(cursoDB == null) {
            log.error("Course with id {} not found.", cursoId);
            return  ResponseEntity.notFound().build();
        }

        estudiantesByCurso = cursoService.listEstudiantesByCursoId(cursoId);
        if (estudiantesByCurso.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(estudiantesByCurso);
    }

    @PostMapping
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso, BindingResult result) {
        log.info("Creating course : {}", curso);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Curso cursoDB = cursoService.createCurso (curso);

        return  ResponseEntity.status( HttpStatus.CREATED).body(cursoDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCurso(@PathVariable("id") long id, @RequestBody Curso curso) {
        log.info("Updating course with id {}", id);

        Curso currentCourse = cursoService.getCurso(id);

        if ( currentCourse == null ) {
            log.error("Unable to update. Course with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        curso.setId(id);
        currentCourse=cursoService.updateCurso(curso);
        return  ResponseEntity.ok(currentCourse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Curso> deleteCurso(@PathVariable("id") long id) {
        log.info("Deleting course with id {}", id);

        Curso curso = cursoService.getCurso(id);

        List<Estudiante> estudiantesDelCurso = cursoService.listEstudiantesByCursoId(curso.getId());

        if(!estudiantesDelCurso.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso tiene estudiantes, no se puede eliminar");
        }

        boolean deleted = cursoService.deleteCurso(curso);

        if(!deleted) {
            log.error("Unable to delete. Course with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
