package com.cleristonmelo.webmatriculas.entities;

import java.io.Serializable;

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
@Table(name = "tb_address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name = "student_id")
	private Long id;
	private Integer zipCode;
	private String district;
	private String number;
	private String complement;
	
    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
	private Student student;
    
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
	
	public Address() {
	}

	public Address(Long id, Integer zipCode, String district, String number, String complement, Student student,
			City city) {
		this.id = id;
		this.zipCode = zipCode;
		this.district = district;
		this.number = number;
		this.complement = complement;
		this.student = student;
		this.city = city;
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

	@JsonIgnore
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
