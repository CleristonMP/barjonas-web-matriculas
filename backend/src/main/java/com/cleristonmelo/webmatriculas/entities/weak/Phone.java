package com.cleristonmelo.webmatriculas.entities.weak;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.Student;

@Entity
@Table(name="tb_phone")
public class Phone implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id	
	private Long number;
    
    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    private Student student;
    
	public Phone() {
	}

	public Phone(Long number, Student student) {
		this.number = number;
		this.student = student;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
