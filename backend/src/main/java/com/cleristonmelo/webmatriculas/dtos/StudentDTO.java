package com.cleristonmelo.webmatriculas.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cleristonmelo.webmatriculas.dtos.weaks.PhoneDTO;
import com.cleristonmelo.webmatriculas.entities.Address;
import com.cleristonmelo.webmatriculas.entities.City;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.enums.Gender;
import com.cleristonmelo.webmatriculas.entities.enums.Nationality;
import com.cleristonmelo.webmatriculas.entities.enums.Race;
import com.cleristonmelo.webmatriculas.entities.weaks.NationalId;
import com.cleristonmelo.webmatriculas.entities.weaks.Phone;

public class StudentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long enrollment;
	
	@Size(min = 3, max = 50, message = "O nome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@Size(min = 3, max = 50, message = "O sobrenome do pai ou da mãe deve ter entre 3 e 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String lastName;
	private Gender gender;
	private Boolean socialAssistance;
	private Race race;
	private String disability;
	private Long socialId;
	private String email;
	private LocalDate birthDate;
	private Nationality nationality;

	private City birthPlace;
	private NationalId nationalId;
	private Address address;
	private SchoolClass schoolClass;
	
	private Set<ParentDTO> parents = new HashSet<>();
	private Set<PhoneDTO> phones = new HashSet<>();

	public StudentDTO() {
	}

	public StudentDTO(Long enrollment, String name, String lastName, Gender gender, Boolean socialAssistance, Race race,
			String disability, Long socialId, String email, LocalDate birthDate, City birthPlace, NationalId nationalId,
			Address address, SchoolClass schoolClass, Nationality nationality) {
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
		this.birthPlace = birthPlace;
		this.nationalId = nationalId;
		this.address = address;
		this.schoolClass = schoolClass;
		this.nationality = nationality;
	}

	public StudentDTO(Student entity) {
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
		this.birthPlace = entity.getBirthPlace();
		this.nationalId = entity.getNationalId();
		this.address = entity.getAddress();
		this.schoolClass = entity.getSchoolClass();
		this.nationality = entity.getNationality();
	}
	
	public StudentDTO(Student entity, Set<Parent> parents) {
		this(entity);
		parents.forEach(parent -> this.parents.add(new ParentDTO(parent)));
	}
	
	public StudentDTO(Student entity, Set<Parent> parents, Set<Phone> phones) {
		this(entity);
		parents.forEach(parent -> this.parents.add(new ParentDTO(parent)));
		phones.forEach(phone -> this.phones.add(new PhoneDTO(phone)));
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

	public City getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(City birthPlace) {
		this.birthPlace = birthPlace;
	}

	public NationalId getNationalId() {
		return nationalId;
	}

	public void setNationalId(NationalId nationalId) {
		this.nationalId = nationalId;
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

	public Set<PhoneDTO> getPhones() {
		return phones;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}
}
