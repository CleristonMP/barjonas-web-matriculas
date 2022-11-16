package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.enums.Period;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;

public class SchoolClassDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String name;
	
	@NotBlank(message = "Campo obrigatório")
	private Period period;

	private Set<StudentDTO> students = new HashSet<>();
	
	public SchoolClassDTO() {
	}

	public SchoolClassDTO(Long id, String name, Period period) {
		this.id = id;
		this.name = name;
		this.period = period;
	}
	
	public SchoolClassDTO(SchoolClass entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.period = entity.getPeriod();
	}
	
	public SchoolClassDTO(SchoolClass entity, Set<Student> students) {
		this(entity);
		students.forEach(student -> this.students.add(new StudentDTO(student)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Set<StudentDTO> getStudents() {
		return students;
	}
}
