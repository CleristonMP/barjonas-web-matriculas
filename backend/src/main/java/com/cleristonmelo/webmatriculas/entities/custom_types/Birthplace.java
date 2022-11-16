package com.cleristonmelo.webmatriculas.entities.custom_types;

import java.io.Serializable;
import java.util.Objects;

public class Birthplace implements Serializable {
	private static final long serialVersionUID = 1L;

	private String city;
	private String state;
	private String country;
	
	public Birthplace() {
	}

	public Birthplace(String city, String state, String country) {
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Birthplace other = (Birthplace) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(state, other.state);
	}
}
