package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.weaks.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {

}
