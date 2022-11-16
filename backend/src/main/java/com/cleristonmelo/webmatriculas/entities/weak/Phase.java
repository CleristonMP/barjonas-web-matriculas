package com.cleristonmelo.webmatriculas.entities.weak;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.SchoolClass;

@Entity
@Table(name="tb_phase")
public class Phase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "school_class_id")
	private Long schoolClassId;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "school_class_id", referencedColumnName = "id")
	private SchoolClass schoolClass;

	private String description;
	
	public Phase() {
	}

	public Phase(Long schoolClassId, SchoolClass schoolClass, String description) {
		this.schoolClassId = schoolClass.getId();
		this.schoolClass = schoolClass;
		this.description = description;
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

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phase other = (Phase) obj;
		return Objects.equals(description, other.description);
	}
}
