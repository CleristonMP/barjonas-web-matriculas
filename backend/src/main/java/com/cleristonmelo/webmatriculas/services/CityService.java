package com.cleristonmelo.webmatriculas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.CityDTO;
import com.cleristonmelo.webmatriculas.entities.City;
import com.cleristonmelo.webmatriculas.repositories.CityRepository;
import com.cleristonmelo.webmatriculas.repositories.StateRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAllPaged() {
		List<City> list = repository.findAll();
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity.setState(stateRepository.findById(dto.getState().getId()).get());
		entity = repository.save(entity);
		return new CityDTO(entity);
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
