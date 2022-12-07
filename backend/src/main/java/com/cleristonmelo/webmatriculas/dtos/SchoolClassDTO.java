package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cleristonmelo.webmatriculas.entities.Phase;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.enums.Period;

public class SchoolClassDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	private String name;
	
	@NotNull(message = "Campo obrigatório")
	private Period period;
	
	@NotNull(message = "Campo obrigatório")
	private Phase phase;

	private Set<StudentSchoolClassDTO> students = new HashSet<>();
	
	public SchoolClassDTO() {
	}
	
	public SchoolClassDTO(Long id, String name, Period period, Phase phase) {
		this.id = id;
		this.name = name;
		this.period = period;
		this.phase = phase;
	}

	public SchoolClassDTO(SchoolClass entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.period = entity.getPeriod();
		this.phase = entity.getPhase();
	}
	
	public SchoolClassDTO(SchoolClass entity, Set<Student> students) {
		this(entity);
		students.forEach(student -> this.students.add(new StudentSchoolClassDTO(student)));
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

	public void setName(String name) {
		this.name = name;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Set<StudentSchoolClassDTO> getStudents() {
		return students;
	}
}
