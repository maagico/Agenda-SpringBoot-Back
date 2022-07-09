package es.agenda.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.agenda.model.Usuario;
import es.agenda.service.UsuarioServiceI;
import es.agenda.util.JWTUtils;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioServiceI usuarioService;	

	@PostMapping("/api/login")
	public ResponseEntity<String> login(HttpServletRequest request, 
										String username, 
										String password)
										throws Exception {
		
		Usuario usuario = usuarioService.findUsuarioByUsuarioYPassword(username, password);
		
		if(usuario != null) {
			
			String roles = usuarioService.findRolesByUsuario(username);
			
			String jwt = JWTUtils.crearToken(usuario.getUsuario(), roles);
			
			return  ResponseEntity.ok().body(jwt);
		}
		
		return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login o password incorrectos");
	}
}
