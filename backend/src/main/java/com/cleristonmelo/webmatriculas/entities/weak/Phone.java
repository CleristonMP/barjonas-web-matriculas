package com.cleristonmelo.webmatriculas.entities.weak;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.Parent;

@Entity
@Table(name="tb_phone")
public class Phone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "parent_id")
	private Long parentId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "parent_id", referencedColumnName = "id")
	private Parent parent;
	
	private Integer number;
	
	public Phone() {
	}

	public Phone(Long parentId, Parent parent, Integer number) {
		this.parentId = parent.getId();
		this.parent = parent;
		this.number = number;
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
