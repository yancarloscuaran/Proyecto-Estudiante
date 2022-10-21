package com.example.demo.service;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Estudiante;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoService cursoService;

    @Override
    public List<Estudiante> findEstudianteAll() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante createEstudiante(Estudiante estudiante) {
        Estudiante estudianteDB = getEstudiante ( estudiante.getId() );

        if (estudianteDB != null){
            return  estudianteDB;
        }

        Curso curso = cursoService.getCurso(estudiante.getCurso().getId());

        if(curso == null) {
            throw  new RuntimeException("Course with id "+estudiante.getCurso().getId()+" doesn't exist");
        }

        estudiante.setCurso(curso);

        estudianteDB = estudianteRepository.save ( estudiante );
        return estudianteDB;
    }

    @Override
    public Estudiante updateEstudiante(Estudiante estudiante) {
        Estudiante estudianteDB = getEstudiante(estudiante.getId());
        if (estudianteDB == null){
            return  null;
        }
        estudianteDB.setNombres(estudiante.getNombres());
        Curso curso = cursoService.getCurso(estudiante.getCurso().getId());
        if(curso == null) {
            throw  new RuntimeException("Course with id "+estudiante.getCurso().getId()+" doesn't exist");
        }

        estudianteDB.setCurso(curso);

        return  estudianteRepository.save(estudianteDB);
    }

    @Override
    public boolean deleteEstudiante(Estudiante estudiante) {
        if(estudiante == null) {
            return false;
        }

        estudianteRepository.delete(estudiante);
        return true;
    }

    @Override
    public Estudiante getEstudiante(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }
}
