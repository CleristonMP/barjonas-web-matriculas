package com.cleristonmelo.webmatriculas.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.cleristonmelo.webmatriculas.entities.enums.Gender;
import com.cleristonmelo.webmatriculas.entities.enums.Race;
import com.cleristonmelo.webmatriculas.entities.weak.Birthplace;
import com.cleristonmelo.webmatriculas.entities.weak.NationalId;
import com.cleristonmelo.webmatriculas.entities.weak.Phone;

@Entity
@Table(name = "tb_student")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long enrollment;
	private String name;
	private String lastName;
	private Boolean socialAssistance;
	private String disability;
	private String email;
	
	@Column(unique = true)
	private Long socialId;
	
	@Enumerated(EnumType.STRING)
	private Race race;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDate birthDate;

	@ManyToOne
	@JoinColumn(name = "birth_place_id", referencedColumnName = "id")
	private Birthplace birthPlace;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.EAGER)
	private NationalId nationalId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	private Set<Parent> parents = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "school_class_id")
	private SchoolClass schoolClass;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private Set<Phone> phones = new HashSet<>();
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	public Student() {
	}
	
	public Student(Long enrollment, String name, String lastName, Long socialId, Gender gender,
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

	public Long getSocialId() {
		return socialId;
	}

	public void setSocialId(Long socialId) {
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

	public Set<Parent> getParents() {
		return parents;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(enrollment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(enrollment, other.enrollment);
	}
}
