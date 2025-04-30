package com.colchoesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.colchoesapi.model.Usuario;
import com.colchoesapi.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    private static final String SECRET_KEY = "";

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = usuarioService.findByEmailAndPassword(usuario.getEmail(), usuario.getPassword());
        
        if (usuarioAutenticado != null) {
            String token = Jwts.builder()
                .setSubject(usuarioAutenticado.getEmail())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
            return token;
        } else {
            return "Credenciais inválidas";
        }
    }
}
