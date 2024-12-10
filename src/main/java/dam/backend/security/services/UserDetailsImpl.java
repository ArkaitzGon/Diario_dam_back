package dam.backend.security.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dam.backend.domain.Usuario;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String nombre;
	
	public UserDetailsImpl(int id, String email, String password, String nombre) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
	}
	
	public UserDetailsImpl(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.password = usuario.getPassword();
		this.nombre = usuario.getNombre();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<GrantedAuthority>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl usuario = (UserDetailsImpl) o;
		return Objects.equals(id, usuario.id);
	}

}
