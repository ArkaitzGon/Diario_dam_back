package com.example.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuration.UsuarioAuthProvider;
import com.example.dto.CredentialsDto;
import com.example.dto.SignUpDto;
import com.example.dto.UsuarioDto;
import com.example.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final UsuarioService usuarioService;
	private final UsuarioAuthProvider usuarioAuthProvider;
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioDto> login(@RequestBody CredentialsDto credentialsDto) {
		UsuarioDto usuario = usuarioService.login(credentialsDto);
		usuario.setToken(usuarioAuthProvider.createToken(usuario));
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDto> register(@RequestBody SignUpDto signUpDto) {
		UsuarioDto usuario = usuarioService.register(signUpDto);
		usuario.setToken(usuarioAuthProvider.createToken(usuario));
		return ResponseEntity.created(URI.create("/usuarios/"+ usuario.getId())).body(usuario);
	}
}
