package com.cleristonmelo.webmatriculas.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.ParentDTO;
import com.cleristonmelo.webmatriculas.dtos.StudentDTO;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.Student;
import com.cleristonmelo.webmatriculas.entities.custom_types.NationalId;
import com.cleristonmelo.webmatriculas.repositories.AddressRepository;
import com.cleristonmelo.webmatriculas.repositories.ParentRepository;
import com.cleristonmelo.webmatriculas.repositories.SchoolClassRepository;
import com.cleristonmelo.webmatriculas.repositories.StudentRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private SchoolClassRepository schoolClassRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Transactional(readOnly = true)
	public Page<StudentDTO> findAllPaged(Pageable pageable, Long schoolClassId, String name) {
		Page<Student> page = repository.find(pageable, schoolClassId, name);
		return page.map(x -> new StudentDTO(x));
	}
	
	@Transactional(readOnly = true)
	public StudentDTO findById(Long id) {
		Optional<Student> obj = repository.findById(id);
		Student entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new StudentDTO(entity);
	}
	
	@Transactional
	public StudentDTO insert(StudentDTO dto) {
		Student entity = new Student();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new StudentDTO(entity);
	}

	@Transactional
	public StudentDTO update(Long id, StudentDTO dto) {
		try {
			Student entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new StudentDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(StudentDTO dto, Student entity) {
		
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		entity.setBirthPlace(dto.getBirthPlace());
		entity.setSocialAssistance(dto.getSocialAssistance());
		entity.setDisability(dto.getDisability());
		entity.setSocialId(dto.getSocialId());
		entity.setNationalId(new NationalId(dto.getNationalId()));
		entity.setEmail(dto.getEmail());
		entity.setRace(dto.getRace());
		entity.setGender(dto.getGender());
		entity.setBirthDate(dto.getBirthDate());
		entity.setAddress(addressRepository.getOne(dto.getAddress().getId()));
		entity.setSchoolClass(schoolClassRepository.getOne(dto.getSchoolClass().getId()));
		
		entity.getParents().clear();
		for (ParentDTO parDto : dto.getParents()) {
			Parent parent = parentRepository.getOne(parDto.getId());
			entity.getParents().add(parent);
		}
	}
}
