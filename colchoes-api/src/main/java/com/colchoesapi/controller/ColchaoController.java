package com.colchoesapi.controller;

import java.util.Optional;
import java.util.List;

import com.colchoesapi.model.Colchao;
import com.colchoesapi.service.ColchaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("colchoes")
public class ColchaoController {

	@Autowired
	private ColchaoService colchaoService;

	@GetMapping
	public List<Colchao> findAll() {
		return (List<Colchao>) colchaoService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Colchao> findById(@PathVariable Long id) {
		Optional<Colchao> colchao = colchaoService.findById(id);
		return colchao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Colchao> save(@RequestBody Colchao colchao) {
		Colchao savedColchao = colchaoService.save(colchao);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedColchao);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Colchao> delete(@PathVariable Long id) {
		Optional<Colchao> colchao = colchaoService.findById(id);

		if (colchao.isPresent()) {
			colchaoService.delete(id);
			return ResponseEntity.ok(colchao.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Colchao> update(@PathVariable Long id, @RequestBody Colchao colchao) {
		Optional<Colchao> existingColchao = colchaoService.findById(id);
		if (existingColchao.isPresent()) {
			colchao.setId(id);
			Colchao updatedColchao = colchaoService.save(colchao);
			return ResponseEntity.ok(updatedColchao);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
