package com.colchoesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import com.colchoesapi.model.Usuario;

import com.colchoesapi.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Cacheable("usuarios")
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Cacheable(value = "usuarioById", key = "#id")
	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	@CacheEvict(value = {"usuarios", "usuarioById"}, allEntries = true)
	public Usuario save(Usuario usuario) {
	    if (usuario.getPassword() == null) {
	        throw new IllegalArgumentException("A senha do usuário não pode ser nula.");
	    }

	    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	    return usuarioRepository.save(usuario);
	}

	@CacheEvict(value = {"usuarios", "usuarioById"}, allEntries = true)
	public void delete (Long id) {
		usuarioRepository.deleteById(id);
	}

	public Usuario findByEmailAndPassword(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return usuario;
            }
        }

        return null;
    }
}
