package com.example.services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.Usuario;
import com.example.dto.CredentialsDto;
import com.example.dto.SignUpDto;
import com.example.dto.UsuarioDto;
import com.example.exceptions.AppException;
import com.example.mappers.UsuarioMapper;
import com.example.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioMapper usuarioMapper;
	
	public UsuarioDto login(CredentialsDto credentialsDto) {
		Usuario usuario = usuarioRepository.findByEmail(credentialsDto.email())
		.orElseThrow( () -> new AppException("Usuario Desconocido",HttpStatus.NOT_FOUND));
		
		if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), usuario.getPassword())) {
			return usuarioMapper.toUsuarioDto(usuario);
		}
		
		throw new AppException("Contraseña invalida", HttpStatus.BAD_REQUEST);
	}
	
	public UsuarioDto register(SignUpDto signUpDto) {
		Optional<Usuario> oUsuario =  usuarioRepository.findByEmail(signUpDto.email());
		
		if (oUsuario.isPresent()) {
			throw new AppException("Este usuario ya existe", HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = usuarioMapper.signUpToUser(signUpDto);
		usuario.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
		Usuario usuarioNuevo = usuarioRepository.save(usuario);
		return usuarioMapper.toUsuarioDto(usuarioNuevo);
	}
}
