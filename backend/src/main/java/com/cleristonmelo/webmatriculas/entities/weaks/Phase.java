package com.cleristonmelo.webmatriculas.entities.weaks;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_phase")
public class Phase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	
	@OneToMany(mappedBy = "phase")
	private Set<SchoolClass> schoolClasses = new HashSet<>();
	
	public Phase() {
	}

	public Phase(Long id, String description) {
		this.id = id;
		this.description = description;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}
}
