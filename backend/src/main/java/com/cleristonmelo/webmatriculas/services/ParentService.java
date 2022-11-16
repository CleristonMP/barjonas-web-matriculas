package com.cleristonmelo.webmatriculas.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleristonmelo.webmatriculas.dtos.ParentDTO;
import com.cleristonmelo.webmatriculas.dtos.weaks.PhoneDTO;
import com.cleristonmelo.webmatriculas.entities.Parent;
import com.cleristonmelo.webmatriculas.entities.weak.Phone;
import com.cleristonmelo.webmatriculas.repositories.ParentRepository;
import com.cleristonmelo.webmatriculas.services.exceptions.DatabaseException;
import com.cleristonmelo.webmatriculas.services.exceptions.ResourceNotFoundException;

@Service
public class ParentService {
	
	@Autowired
	private ParentRepository repository;
	
	@Transactional(readOnly = true)
	public ParentDTO findById(Long id) {
		Optional<Parent> obj = repository.findById(id);
		Parent entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ParentDTO(entity, entity.getPhones());
	}
	
	@Transactional
	public ParentDTO insert(ParentDTO dto) {
		Parent entity = new Parent();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ParentDTO(entity);
	}

	@Transactional
	public ParentDTO update(Long id, ParentDTO dto) {
		try {
			Parent entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ParentDTO(entity);
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

	private void copyDtoToEntity(ParentDTO dto, Parent entity) {
		entity.setName(dto.getName());
		entity.setLastName(dto.getLastName());
		
		entity.getPhones().clear();
		for (PhoneDTO phoneDto : dto.getPhones()) {
			Phone phone = new Phone();
			phone.setNumber(phoneDto.getNumber());
			phone.setParent(repository.getOne(phoneDto.getParentId()));
			entity.getPhones().add(phone);
		}
	}	
}
