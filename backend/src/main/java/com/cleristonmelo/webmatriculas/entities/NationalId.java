package com.cleristonmelo.webmatriculas.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_national_id")
public class NationalId implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "student_id")
	private Long id;
    
    @Column(unique = true)
	private Long number;
	private String issuingEntity;

    @ManyToOne
    @JoinColumn(name="city_id", insertable = false, updatable = false)
	private City city;
	
    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
	private Student student;
	
	public NationalId() {
	}

	public NationalId(Long id, Long number, String issuingEntity, String state, City city, Student student) {
		this.id = id;
		this.number = number;
		this.issuingEntity = issuingEntity;
		this.city = city;
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getIssuingEntity() {
		return issuingEntity;
	}

	public void setIssuingEntity(String issuingEntity) {
		this.issuingEntity = issuingEntity;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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
