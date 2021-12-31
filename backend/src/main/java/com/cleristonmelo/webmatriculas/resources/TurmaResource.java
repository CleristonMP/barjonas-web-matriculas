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

import com.cleristonmelo.webmatriculas.dtos.TurmaDTO;
import com.cleristonmelo.webmatriculas.services.TurmaService;

@RestController
@RequestMapping(value = "/turmas")
public class TurmaResource {
	
	@Autowired
	private TurmaService service;
	
	@GetMapping
	public ResponseEntity<Page<TurmaDTO>> findAll(Pageable pageable) {
		Page<TurmaDTO> page = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> findById(@PathVariable Long id){
		TurmaDTO dto = service.findById(id);	
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<TurmaDTO> insert(@Valid @RequestBody TurmaDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> update(@PathVariable Long id, @Valid @RequestBody TurmaDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
