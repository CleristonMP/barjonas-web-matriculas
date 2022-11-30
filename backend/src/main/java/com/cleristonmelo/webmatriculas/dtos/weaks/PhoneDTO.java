package com.cleristonmelo.webmatriculas.dtos.weaks;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.weaks.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long number;
	
	private Student student;
	
	public PhoneDTO() {
	}

	public PhoneDTO(Long number, Student student) {
		this.number = number;
		this.student = student;
	}

	public PhoneDTO(Phone entity) {
		this.number = entity.getNumber();
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
