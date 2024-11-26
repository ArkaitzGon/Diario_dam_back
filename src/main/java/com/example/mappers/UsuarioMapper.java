package com.example.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.domain.Usuario;
import com.example.dto.SignUpDto;
import com.example.dto.UsuarioDto;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioDto toUsuarioDto(Usuario usuario);

	@Mapping(target = "password", ignore = true)
	Usuario signUpToUser(SignUpDto signUpDto);
	
}
