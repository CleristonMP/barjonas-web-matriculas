package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.NationalId;

@Repository
public interface NationalIdRepository extends JpaRepository<NationalId, Long> {

}
