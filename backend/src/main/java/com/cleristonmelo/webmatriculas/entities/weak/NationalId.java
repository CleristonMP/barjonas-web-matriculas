package com.cleristonmelo.webmatriculas.entities.weak;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_national_id")
public class NationalId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	private Long number;
	private String issuingEntity;
	private String state;
	private String city;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	public NationalId() {
	}

	public NationalId(Long number, String issuingEntity, String state, String city) {
		this.number = number;
		this.issuingEntity = issuingEntity;
		this.state = state;
		this.city = city;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getIssuingEntity() {
		return issuingEntity;
	}

	public void setIssuingEntity(String issuingEntity) {
		this.issuingEntity = issuingEntity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NationalId other = (NationalId) obj;
		return Objects.equals(number, other.number);
	}
}
