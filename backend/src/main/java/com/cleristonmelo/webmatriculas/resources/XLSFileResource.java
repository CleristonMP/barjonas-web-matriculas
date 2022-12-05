package com.cleristonmelo.webmatriculas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cleristonmelo.webmatriculas.services.XLSFileService;

@RestController
@RequestMapping(value = "/xls-file-upload")
public class XLSFileResource {
	
	@Autowired
	private XLSFileService service;
	
	@PostMapping
	public ResponseEntity<Void> uploadXLSFile(@RequestParam("file") MultipartFile file){
		service.getDataFromXLSFile(file);
		return ResponseEntity.ok().build();
	}
}
