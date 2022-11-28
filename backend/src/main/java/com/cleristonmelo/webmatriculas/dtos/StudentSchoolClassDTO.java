package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.enums.Gender;
import com.cleristonmelo.webmatriculas.entities.enums.Race;

public class StudentSchoolClassDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long enrollment;
	private String name;
	private String lastName;
	private Gender gender;
	private Boolean socialAssistance;
	private Race race;
	private String disability;
	private Long socialId;
	private String email;
	private LocalDate birthDate;

	public StudentSchoolClassDTO() {
	}

	public StudentSchoolClassDTO(Long enrollment, String name, String lastName, Gender gender, Boolean socialAssistance,
			Race race, String disability, Long socialId, String email, LocalDate birthDate) {
		this.enrollment = enrollment;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.socialAssistance = socialAssistance;
		this.race = race;
		this.disability = disability;
		this.socialId = socialId;
		this.email = email;
		this.birthDate = birthDate;
	}

	public StudentSchoolClassDTO(Student entity) {
		this.enrollment = entity.getEnrollment();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
		this.gender = entity.getGender();
		this.socialAssistance = entity.getSocialAssistance();
		this.race = entity.getRace();
		this.disability = entity.getDisability();
		this.socialId = entity.getSocialId();
		this.email = entity.getEmail();
		this.birthDate = entity.getBirthDate();
	}
	public Long getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Long enrollment) {
		this.enrollment = enrollment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Boolean getSocialAssistance() {
		return socialAssistance;
	}

	public void setSocialAssistance(Boolean socialAssistance) {
		this.socialAssistance = socialAssistance;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public Long getSocialId() {
		return socialId;
	}

	public void setSocialId(Long socialId) {
		this.socialId = socialId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}
