package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.Address;
import com.cleristonmelo.webmatriculas.entities.City;

public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer zipCode;
	private String district;
	private String number;
	private String complement;
	
	private City city;
	
	public AddressDTO() {
	}

	public AddressDTO(Long id, Integer zipCode, String district, String number, String complement, City city) {
		this.id = id;
		this.zipCode = zipCode;
		this.district = district;
		this.number = number;
		this.complement = complement;
		this.city = city;
	}
	
	public AddressDTO(Address entity) {
		this.id = entity.getId();
		this.zipCode = entity.getZipCode();
		this.district = entity.getDistrict();
		this.number = entity.getNumber();
		this.complement = entity.getComplement();
		this.city = entity.getCity();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
