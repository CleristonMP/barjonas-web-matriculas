package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cleristonmelo.webmatriculas.entities.Parent;

public class ParentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(min = 3, max = 50, message = "O nome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;

	@Size(min = 3, max = 50, message = "O sobrenome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String lastName;

	public ParentDTO() {
	}

	public ParentDTO(Long id, String name, String lastName) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
	}

	public ParentDTO(Parent entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
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
}
