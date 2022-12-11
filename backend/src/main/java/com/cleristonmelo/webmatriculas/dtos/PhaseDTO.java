package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.cleristonmelo.webmatriculas.entities.Phase;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String description;

	private Set<SchoolClass> schoolClasses = new HashSet<>();
	
	public PhaseDTO() {
	}

	public PhaseDTO(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public PhaseDTO(Phase entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public Set<SchoolClass> getSchoolClasses() {
		return schoolClasses;
	}
}
