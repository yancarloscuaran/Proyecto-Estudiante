package com.example.demo.service;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Estudiante;

import java.util.List;

public interface CursoService {

    public List<Curso> findCursoAll();

    public Curso createCurso(Curso curso);
    public Curso updateCurso(Curso curso);
    public boolean deleteCurso(Curso curso);
    public Curso getCurso(Long id);
    public List<Estudiante> listEstudiantesByCursoId(Long id);
}
