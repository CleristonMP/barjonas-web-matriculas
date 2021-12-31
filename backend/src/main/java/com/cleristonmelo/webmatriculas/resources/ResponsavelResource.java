package com.cleristonmelo.webmatriculas.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cleristonmelo.webmatriculas.dtos.ResponsavelDTO;
import com.cleristonmelo.webmatriculas.services.ResponsavelService;

@RestController
@RequestMapping(value = "/responsaveis")
public class ResponsavelResource {
	
	@Autowired
	private ResponsavelService service;
	
	@GetMapping
	public ResponseEntity<Page<ResponsavelDTO>> findAll(Pageable pageable) {
		Page<ResponsavelDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponsavelDTO> findById(@PathVariable Long id){
		ResponsavelDTO dto = service.findById(id);	
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ResponsavelDTO> insert(@Valid @RequestBody ResponsavelDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponsavelDTO> update(@PathVariable Long id, @Valid @RequestBody ResponsavelDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
