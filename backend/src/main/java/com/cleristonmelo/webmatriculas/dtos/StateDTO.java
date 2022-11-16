package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.cleristonmelo.webmatriculas.entities.City;
import com.cleristonmelo.webmatriculas.entities.State;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	private Set<CityDTO> cities = new HashSet<>();
	
	public StateDTO() {
	}

	public StateDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public StateDTO(State entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
	
	public StateDTO(State entity, Set<City> cities) {
		this(entity);
		cities.forEach(city -> this.cities.add(new CityDTO(city)));
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

	public Set<CityDTO> getCities() {
		return cities;
	}
}
