package com.colchoesapi.controller;

import java.util.Optional;
import java.util.List;

import com.colchoesapi.model.Usuario;
import com.colchoesapi.service.UsuarioService;

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
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
		Usuario savedUsuario = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> delete(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.findById(id);

		if (usuario.isPresent()) {
			usuarioService.delete(id);
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
		Optional<Usuario> existingUsuario = usuarioService.findById(id);
		if (existingUsuario.isPresent()) {
			usuario.setId(id);
			Usuario updatedUsuario = usuarioService.save(usuario);
			return ResponseEntity.ok(updatedUsuario);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
