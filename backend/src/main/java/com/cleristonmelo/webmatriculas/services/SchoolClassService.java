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

import com.cleristonmelo.webmatriculas.dtos.SchoolClassDTO;
import com.cleristonmelo.webmatriculas.entities.SchoolClass;
import com.cleristonmelo.webmatriculas.entities.enums.Period;
import com.cleristonmelo.webmatriculas.repositories.SchoolClassRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class SchoolClassService {
	
	@Autowired
	private SchoolClassRepository repository;
	
	@Transactional(readOnly = true)
	public Page<SchoolClassDTO> findAllPaged(Pageable pageable, String name, Period period) {
		Page<SchoolClass> page = repository.find(pageable, name, period);
		return page.map(x -> new SchoolClassDTO(x));
	}
	
	@Transactional(readOnly = true)
	public SchoolClassDTO findById(Long id) {
		Optional<SchoolClass> obj = repository.findById(id);
		SchoolClass entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new SchoolClassDTO(entity, entity.getStudents());
	}
	
	@Transactional
	public SchoolClassDTO insert(SchoolClassDTO dto) {
		SchoolClass entity = new SchoolClass();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new SchoolClassDTO(entity);
	}

	@Transactional
	public SchoolClassDTO update(Long id, SchoolClassDTO dto) {
		try {
			SchoolClass entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new SchoolClassDTO(entity);
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

	private void copyDtoToEntity(SchoolClassDTO dto, SchoolClass entity) {
		entity.setName(dto.getName());
		entity.setPeriod(dto.getPeriod());
		entity.setPhase(dto.getPhase());
	}	
}
