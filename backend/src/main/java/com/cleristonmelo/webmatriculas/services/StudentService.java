package com.cleristonmelo.webmatriculas.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.ParentDTO;
import com.cleristonmelo.webmatriculas.dtos.PhoneDTO;
import com.cleristonmelo.webmatriculas.dtos.StudentDTO;
import com.cleristonmelo.webmatriculas.entities.Address;
import com.cleristonmelo.webmatriculas.entities.NationalId;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.Phone;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.repositories.AddressRepository;
import com.cleristonmelo.webmatriculas.repositories.CityRepository;
import com.cleristonmelo.webmatriculas.repositories.NationalIdRepository;
import com.cleristonmelo.webmatriculas.repositories.ParentRepository;
import com.cleristonmelo.webmatriculas.repositories.PhoneRepository;
import com.cleristonmelo.webmatriculas.repositories.SchoolClassRepository;
import com.cleristonmelo.webmatriculas.repositories.StudentRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private SchoolClassRepository schoolClassRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private NationalIdRepository nationalIdRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PhoneRepository phoneRepository;

	@Transactional(readOnly = true)
	public Page<StudentDTO> findAllPaged(Pageable pageable, Long schoolClassId, String name) {
		Page<Student> page = repository.find(pageable, schoolClassId, name);
		return page.map(x -> new StudentDTO(x));
	}

	@Transactional(readOnly = true)
	public StudentDTO findByEnrollment(Long enrollment) {
		Optional<Student> obj = repository.findById(enrollment);
		Student entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new StudentDTO(entity, entity.getParents(), entity.getPhones());
	}

	@Transactional
	public StudentDTO insert(StudentDTO dto) {
		Student entity = new Student();
		copyDtoToEntity(dto, entity, false);
		entity = repository.save(entity);
		return new StudentDTO(entity, entity.getParents(), entity.getPhones());
	}

	@Transactional
	public StudentDTO update(Long enrollment, StudentDTO dto) {
		try {
			Student entity = repository.getOne(enrollment);
			copyDtoToEntity(dto, entity, true);
			entity = repository.save(entity);
			return new StudentDTO(entity, entity.getParents(), entity.getPhones());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + enrollment);
		}
	}

	public void delete(Long enrollment) {
		try {
			repository.deleteById(enrollment);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + enrollment);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(StudentDTO dto, Student entity, boolean isEditing) {
		entity.setEnrollment(dto.getEnrollment());
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		entity.setSocialAssistance(dto.getSocialAssistance());
		entity.setDisability(dto.getDisability());
		entity.setSocialId(dto.getSocialId());
		entity.setEmail(dto.getEmail());
		entity.setRace(dto.getRace());
		entity.setGender(dto.getGender());
		entity.setBirthDate(dto.getBirthDate());
		entity.setNationality(dto.getNationality());

		NationalId nationalId = new NationalId();
		Address address = new Address();

		if (!isEditing) {
			if (dto.getBirthPlace() != null) {
				entity.setBirthPlace(cityRepository.findById(dto.getBirthPlace().getId()).get());
			}
			nationalId.setStudent(entity);
			address.setStudent(entity);
		} else {
			entity.setBirthPlace(dto.getBirthPlace());
			nationalId = nationalIdRepository.getOne(dto.getEnrollment());
			address = addressRepository.getOne(dto.getEnrollment());
		}

		if (dto.getNationalId() != null) {
			nationalId.setNumber(dto.getNationalId().getNumber());
			nationalId.setIssuingEntity(dto.getNationalId().getIssuingEntity());
			if (dto.getNationalId().getCity() != null) {
				nationalId.setCity(cityRepository.getOne(dto.getNationalId().getCity().getId()));
			}
		}

		entity.setNationalId(nationalId);

		address.setZipCode(dto.getAddress().getZipCode());
		address.setDistrict(dto.getAddress().getDistrict());
		address.setNumber(dto.getAddress().getNumber());
		address.setComplement(dto.getAddress().getComplement());
		if (dto.getAddress().getCity() != null) {
			address.setCity(cityRepository.getOne(dto.getAddress().getCity().getId()));
		}

		entity.setAddress(address);

		if (dto.getSchoolClass() != null) {
			entity.setSchoolClass(schoolClassRepository.findById(dto.getSchoolClass().getId()).get());
		}

		Set<Parent> savedParents = new HashSet<>();
		for (ParentDTO prtDto : dto.getParents()) {
			if (prtDto != null) {
				Parent obj = null;
				if (prtDto.getName() != "") {
					obj = parentRepository.findByNameAndLastName(prtDto.getName(), prtDto.getLastName());
				}
				if (obj == null && prtDto.getName() != "") {
					Parent prt = new Parent();
					prt.setName(prtDto.getName());
					prt.setLastName(prtDto.getLastName());
					prt = parentRepository.save(prt);
					savedParents.add(prt);
				} else if (obj != null) {
					savedParents.add(obj);
				}
			}
		}

		entity.getParents().clear();
		for (Parent prt : savedParents) {
			Parent parent = parentRepository.getOne(prt.getId());
			entity.getParents().add(parent);
		}

		Set<Phone> savedPhones = new HashSet<>();
		for (PhoneDTO phnDto : dto.getPhones()) {
			if (phnDto != null && phnDto.getNumber() != null) {
				Optional<Phone> obj = phoneRepository.findById(phnDto.getNumber());
				if (obj.isEmpty() && !isEditing) {
					Phone phn = new Phone();
					phn.setNumber(phnDto.getNumber());
					phn = phoneRepository.save(phn);
					savedPhones.add(phn);
				} else {
					savedPhones.add(obj.get());
				}
			}
		}

		entity.getPhones().clear();
		for (Phone phn : savedPhones) {
			Phone phone = phoneRepository.getOne(phn.getNumber());
			entity.getPhones().add(phone);
		}
	}
}
