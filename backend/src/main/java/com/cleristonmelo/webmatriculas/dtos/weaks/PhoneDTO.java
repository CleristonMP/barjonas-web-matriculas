package com.cleristonmelo.webmatriculas.dtos.weaks;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.weak.Phone;


public class PhoneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long number;
	
	public PhoneDTO() {
	}

	public PhoneDTO(Long number) {
		this.number = number;
	}

	public PhoneDTO(Phone entity) {
		this.number = entity.getNumber();
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
}
