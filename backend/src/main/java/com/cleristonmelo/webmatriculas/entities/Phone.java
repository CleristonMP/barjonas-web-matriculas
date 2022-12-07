package com.cleristonmelo.webmatriculas.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_phone")
public class Phone implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id	
	private Long number;
    
    @ManyToMany(mappedBy = "phones")
    private Set<Student> students;
    
	public Phone() {
	}

	public Phone(Long number) {
		this.number = number;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@JsonIgnore
	public Set<Student> getStudents() {
		return students;
	}
}
