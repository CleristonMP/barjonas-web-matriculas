package com.cleristonmelo.webmatriculas.entities.custom_types;

import java.io.Serializable;
import java.util.Objects;

public class NationalId implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer number;
	private String issuingEntity;
	private String state;
	private String city;
	
	public NationalId() {
	}

	public NationalId(Integer number, String issuingEntity, String state, String city) {
		super();
		this.number = number;
		this.issuingEntity = issuingEntity;
		this.state = state;
		this.city = city;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
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
