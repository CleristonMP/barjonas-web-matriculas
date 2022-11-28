package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.City;
import com.cleristonmelo.webmatriculas.entities.State;

public class CityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private State state;
	
	public CityDTO() {
	}

	public CityDTO(Long id, String name, State state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}
	
	public CityDTO(City entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.state = entity.getState();
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
