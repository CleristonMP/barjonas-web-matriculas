package com.cleristonmelo.webmatriculas.entities.weak;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_phone")
public class Phone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	private Long number;
	
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
		Phone other = (Phone) obj;
		return Objects.equals(number, other.number);
	}
}
