package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
