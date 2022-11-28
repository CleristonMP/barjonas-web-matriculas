package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

	Parent findByNameAndLastName(String name, String lastName);
}
