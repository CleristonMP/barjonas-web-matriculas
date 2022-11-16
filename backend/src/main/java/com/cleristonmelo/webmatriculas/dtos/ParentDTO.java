package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cleristonmelo.webmatriculas.dtos.weaks.PhoneDTO;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.weak.Phone;

public class ParentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Size(min = 3, max = 50, message = "O nome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@Size(min = 3, max = 50, message = "O sobrenome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String lastName;
	
	private Set<PhoneDTO> phones = new HashSet<>();
	
	private Student student;
	
	public ParentDTO() {
	}

	public ParentDTO(Long id, String name, String lastName, Student student) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.student = student;
	}
	
	public ParentDTO(Parent entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
		this.student = entity.getStudent();
	}
	
	public ParentDTO(Parent entity, Set<Phone> phones) {
		this(entity);
		phones.forEach(phone -> this.phones.add(new PhoneDTO(phone)));
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Set<PhoneDTO> getPhones() {
		return phones;
	}
}
