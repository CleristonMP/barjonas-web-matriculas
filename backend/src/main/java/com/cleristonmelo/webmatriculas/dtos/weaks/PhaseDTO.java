package com.cleristonmelo.webmatriculas.dtos.weaks;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.weak.Phase;

public class PhaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String description;
	
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
}
