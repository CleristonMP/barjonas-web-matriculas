package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.State;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String country;
	
	public StateDTO() {
	}

	public StateDTO(Long id, String name, String country) {
		this.id = id;
		this.name = name;
		this.country = country;
	}
	
	public StateDTO(State entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.country = entity.getCountry();
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
