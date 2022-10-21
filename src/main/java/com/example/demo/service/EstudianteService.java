package com.example.demo.service;

import com.example.demo.entity.Estudiante;

import java.util.List;

public interface EstudianteService {

    public List<Estudiante> findEstudianteAll();

    public Estudiante createEstudiante(Estudiante estudiante);
    public Estudiante updateEstudiante(Estudiante estudiante);
    public boolean deleteEstudiante(Estudiante estudiante);
    public Estudiante getEstudiante(Long id);
}
