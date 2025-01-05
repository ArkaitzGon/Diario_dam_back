package dam.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dam.backend.domain.Usuario;
import dam.backend.payload.request.LoginRequest;
import dam.backend.payload.request.RegisterRequest;
import dam.backend.payload.response.MessageResponse;
import dam.backend.payload.response.UserInfoResponse;
import dam.backend.repository.UsuarioRepository;
import dam.backend.security.jwt.JwtUtils;
import dam.backend.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(/*@Valid*/ @RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getEmail());
		System.out.println(loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		
		//ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(/*userDetails*/loginRequest.getEmail());
		
		/*return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(userDetails.getId(),userDetails.getUsername(),userDetails.getNombre()));*/
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtUtils.generateJwtCookie(userDetails).toString())
			       .body(new UserInfoResponse(userDetails.getId(),userDetails.getUsername(),userDetails.getNombre()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(/*@Valid*/ @RequestBody RegisterRequest registerRequest) {
		if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Ya existe una cuenta con este correo electronico"));
		}
		
		Usuario usuario = new Usuario(registerRequest.getEmail(),encoder.encode(registerRequest.getPassword()),registerRequest.getNombre());
		
		usuarioRepository.save(usuario);
		
		return ResponseEntity.ok(new MessageResponse("Nuevo usuario registrado correctamente"));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser() {
		/*ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("Sesion cerrada correctamente"));*/
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtUtils.getCleanJwtCookie().toString()).body(new MessageResponse("Sesion cerrada correctamente"));
	}
}
