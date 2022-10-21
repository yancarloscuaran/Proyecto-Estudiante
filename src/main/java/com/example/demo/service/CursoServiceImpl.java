package com.example.demo.service;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Estudiante;
import com.example.demo.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<Curso> findCursoAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso createCurso(Curso curso) {
        Curso cursoDB = null;

        cursoDB = cursoRepository.save ( curso );
        return cursoDB;
    }

    @Override
    public Curso updateCurso(Curso curso) {
        Curso cursoDB = getCurso(curso.getId());
        if (cursoDB == null){
            return  null;
        }
        cursoDB.setCurso(curso.getCurso());
        return cursoRepository.save(cursoDB);
    }

    @Override
    public boolean deleteCurso(Curso curso) {
        if(curso == null) {
            return false;
        }



        cursoRepository.delete(curso);
        return true;
    }

    @Override
    public Curso getCurso(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Estudiante> listEstudiantesByCursoId(Long id) {
        return cursoRepository.listEstudiantesByCursoId(id);
    }
}
