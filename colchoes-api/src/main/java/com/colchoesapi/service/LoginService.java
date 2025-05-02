package com.colchoesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.colchoesapi.model.Usuario;
import com.colchoesapi.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "minhaChaveSecreta123456";

    public String autenticar(String email, String password) {

        if (password != null) {
            password = password.trim();
        }

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            return null;
        }

        boolean senhaCorreta = passwordEncoder.matches(password, usuario.getPassword());

        if (senhaCorreta) {
            String token = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
            return token;
        }

        return null;
    }
}