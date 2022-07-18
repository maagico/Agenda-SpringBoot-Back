package es.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.agenda.json.TokenJSON;
import es.agenda.model.Usuario;
import es.agenda.service.UsuarioServiceI;
import es.agenda.util.JWTUtils;

@Controller
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	private UsuarioServiceI usuarioService;	

	@PostMapping("/login")
	public ResponseEntity<TokenJSON> login(String username, 
										   String password)
										   throws Exception {
		
		Usuario usuario = usuarioService.findUsuarioByUsuarioYPassword(username, password);
		
		TokenJSON tokenJSON = new TokenJSON();
		
		if(usuario != null) {
			
			String roles = usuarioService.findRolesByUsuario(username);
			
			String jwt = JWTUtils.crearToken(usuario.getId(), usuario.getUsuario(), roles);
			
			tokenJSON.setOk(true);
			tokenJSON.setToken(jwt);
			
			return  ResponseEntity.ok().body(tokenJSON);
		}
		
		tokenJSON.setOk(false);
		tokenJSON.setToken("");
		
		return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenJSON);
	}
}
