package com.example.demo.repository;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("FROM Estudiante e WHERE e.curso.id = ?1")
    List<Estudiante> listEstudiantesByCursoId(Long id);
}
