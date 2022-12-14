package com.cleristonmelo.webmatriculas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.StateDTO;
import com.cleristonmelo.webmatriculas.entities.State;
import com.cleristonmelo.webmatriculas.repositories.StateRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class StateService {
	
	@Autowired
	private StateRepository repository;
	
	@Transactional(readOnly = true)
	public List<StateDTO> findAllPaged() {
		List<State> list = repository.findAll();
		return list.stream().map(x -> new StateDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public StateDTO insert(StateDTO dto) {
		State entity = new State();
		entity.setName(dto.getName());
		entity.setCountry(dto.getCountry());
		entity = repository.save(entity);
		return new StateDTO(entity);
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
