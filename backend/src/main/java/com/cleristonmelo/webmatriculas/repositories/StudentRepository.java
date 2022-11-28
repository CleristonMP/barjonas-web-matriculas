package com.cleristonmelo.webmatriculas.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cleristonmelo.webmatriculas.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("SELECT DISTINCT obj FROM Student obj "
			+ "WHERE (COALESCE(:scId) IS NULL OR obj.schoolClass.id = :scId) "
			+ "AND (LOWER(obj.name) LIKE LOWER(CONCAT('%',:stdName,'%')) OR "
			+ "LOWER(obj.lastName) LIKE LOWER(CONCAT('%',:stdName,'%')))")
	Page<Student> find(Pageable pageable, @Param("scId") Long schoolClassId, @Param("stdName") String studentName);

	@Query("SELECT obj FROM Student obj JOIN FETCH obj.parents prts WHERE obj IN :students")
	List<Student> findAllStudentsWithParents(List<Student> students);

//	@Query("SELECT obj FROM Student obj JOIN FETCH obj.phones phns WHERE obj IN :students")
//	List<Student> findStudentsWithPhones(List<Student> students);
}
