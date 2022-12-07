package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	State findByNameLike(String name);
}
