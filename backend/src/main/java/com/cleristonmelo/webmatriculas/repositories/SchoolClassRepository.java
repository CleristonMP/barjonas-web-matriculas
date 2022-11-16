package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	@Query("SELECT obj FROM SchoolClass obj WHERE (LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%')))")
	Page<SchoolClass> find(Pageable pageable, String name);
}
