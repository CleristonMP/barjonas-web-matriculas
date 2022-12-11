package com.cleristonmelo.webmatriculas.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.PhaseDTO;
import com.cleristonmelo.webmatriculas.entities.Phase;
import com.cleristonmelo.webmatriculas.repositories.PhaseRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class PhaseService {
	
	@Autowired
	private PhaseRepository repository;
	
	@Transactional(readOnly = true)
	public List<PhaseDTO> findAllPaged() {
		List<Phase> list = repository.findAll();
		return list.stream().map(x -> new PhaseDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public PhaseDTO insert(PhaseDTO dto) {
		Phase entity = new Phase();
		entity.setDescription(dto.getDescription());
		entity = repository.save(entity);
		return new PhaseDTO(entity);
	}
	
	@Transactional
	public PhaseDTO update(Long id, PhaseDTO dto) {
		try {
			Phase entity = repository.getOne(id);
			entity.setDescription(dto.getDescription());
			entity = repository.save(entity);
			return new PhaseDTO(entity);
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
}
