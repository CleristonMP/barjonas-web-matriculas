package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.cleristonmelo.webmatriculas.entities.Address;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.custom_types.Birthplace;
import com.cleristonmelo.webmatriculas.entities.custom_types.NationalId;
import com.cleristonmelo.webmatriculas.entities.enums.Gender;
import com.cleristonmelo.webmatriculas.entities.enums.Race;

public class StudentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer enrollment;
	private String name;
	private String lastName;
	private Gender gender;
	private Birthplace birthPlace;
	private Boolean socialAssistance;
	private Race race;
	private String disability;
	
	private String socialId;
	private NationalId nationalId;
	private String email;
	
	private LocalDate birthDate;
	
	private Address address;
	
	private SchoolClass schoolClass;
	
	private Set<ParentDTO> parents = new HashSet<>();
	
	public StudentDTO() {
	}
	
	public StudentDTO(Integer enrollment, String name, String lastName, String socialId, Gender gender,
			Birthplace birthPlace, Boolean socialAssistance, Race race, String disability, NationalId nationalId,
			String email, LocalDate birthDate, Address address, SchoolClass schoolClass) {
		this.enrollment = enrollment;
		this.name = name;
		this.lastName = lastName;
		this.socialId = socialId;
		this.gender = gender;
		this.birthPlace = birthPlace;
		this.socialAssistance = socialAssistance;
		this.race = race;
		this.disability = disability;
		this.nationalId = nationalId;
		this.email = email;
		this.birthDate = birthDate;
		this.address = address;
		this.schoolClass = schoolClass;
	}
	
	public StudentDTO(Student entity) {
		this.enrollment = entity.getEnrollment();
		this.name = entity.getName();
		this.lastName = entity.getLastName();
		this.socialId = entity.getSocialId();
		this.gender = entity.getGender();
		this.birthPlace = entity.getBirthPlace();
		this.socialAssistance = entity.getSocialAssistance();
		this.race = entity.getRace();
		this.disability = entity.getDisability();
		this.nationalId = entity.getNationalId();
		this.email = entity.getEmail();
		this.birthDate = entity.getBirthDate();
		this.address = entity.getAddress();
		this.schoolClass = entity.getSchoolClass();
	}
	
	public StudentDTO(Student entity, Set<Parent> parents) {
		this(entity);
		parents.forEach(parent -> this.parents.add(new ParentDTO(parent)));
	}
	
	public Integer getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Integer enrollment) {
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

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Birthplace getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(Birthplace birthPlace) {
		this.birthPlace = birthPlace;
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

	public NationalId getNationalId() {
		return nationalId;
	}

	public void setNationalId(NationalId nationalId) {
		this.nationalId = nationalId;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public Set<ParentDTO> getParents() {
		return parents;
	}
}
