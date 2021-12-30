package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
