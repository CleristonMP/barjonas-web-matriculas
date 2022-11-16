package com.cleristonmelo.webmatriculas.dtos.weaks;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.weak.Phase;

public class PhaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long schoolClassId;
	private SchoolClass schoolClass;
	private String description;
	
	public PhaseDTO() {
	}

	public PhaseDTO(Long schoolClassId, SchoolClass schoolClass, String description) {
		this.schoolClassId = schoolClass.getId();
		this.schoolClass = schoolClass;
		this.description = description;
	}
	
	public PhaseDTO(Phase entity) {
		this.schoolClassId = entity.getSchoolClass().getId();
		this.schoolClass = entity.getSchoolClass();
		this.description = entity.getDescription();
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
		this.schoolClassId = schoolClass.getId();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSchoolClassId() {
		return schoolClassId;
	}
}
