package com.cleristonmelo.webmatriculas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.enums.Period;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

	@Query("SELECT DISTINCT obj FROM SchoolClass obj WHERE "
			+ "(LOWER(obj.name) LIKE LOWER(CONCAT('%',:scName,'%')) "
			+ "AND (COALESCE(:scPeriod) IS NULL OR obj.period = :scPeriod))")
	Page<SchoolClass> find(Pageable pageable, @Param("scName") String schoolClassName,
			@Param("scPeriod") Period schoolClassPeriod);

	@Query("SELECT obj FROM SchoolClass obj JOIN FETCH obj.students")
	SchoolClass findSchoolClassWithStudents(SchoolClass schoolClass);
	
	SchoolClass findByName(String name);
}
