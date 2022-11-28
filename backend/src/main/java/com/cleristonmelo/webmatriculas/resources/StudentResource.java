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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cleristonmelo.webmatriculas.dtos.StudentDTO;
import com.cleristonmelo.webmatriculas.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentResource {
	
	@Autowired
	private StudentService service;
	
	@GetMapping
	public ResponseEntity<Page<StudentDTO>> findAll(Pageable pageable,
			@RequestParam(value = "schoolClassId", defaultValue = "") Long schoolClassId,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		Page<StudentDTO> page = service.findAllPaged(pageable, schoolClassId, name.trim());
		
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping(value = "/{enrollment}")
	public ResponseEntity<StudentDTO> findByEnrollment(@PathVariable Long enrollment){
		StudentDTO dto = service.findByEnrollment(enrollment);	
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<StudentDTO> insert(@Valid @RequestBody StudentDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{enrollment}")
				.buildAndExpand(dto.getEnrollment()).toUri();		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{enrollment}")
	public ResponseEntity<StudentDTO> update(@PathVariable Long enrollment, @Valid @RequestBody StudentDTO dto) {
		dto = service.update(enrollment, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{enrollment}")
	public ResponseEntity<Void> delete(@PathVariable Long enrollment) {
		service.delete(enrollment);
		return ResponseEntity.noContent().build();
	}
}
