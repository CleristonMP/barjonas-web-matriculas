package com.cleristonmelo.webmatriculas.dtos.weaks;

import java.io.Serializable;

import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.weak.Phone;


public class PhoneDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long parentId;
	private Parent parent;
	private Integer number;
	
	public PhoneDTO() {
	}

	public PhoneDTO(Long parentId, Parent parent, Integer number) {
		this.parentId = parent.getId();
		this.parent = parent;
		this.number = number;
	}
	
	public PhoneDTO(Phone entity) {
		this.parentId = entity.getParent().getId();
		this.parent = entity.getParent();
		this.number = entity.getNumber();
	}

	public Long getParentId() {
		return parentId;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
		this.parentId = parent.getId();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
