package es.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.agenda.json.UsuarioJSON;
import es.agenda.service.UsuarioServiceI;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioServiceI usuarioService;
	
	@GetMapping(value = "/usuarios")
	public ResponseEntity<List<UsuarioJSON>> getUsuarios() {
	
		List<UsuarioJSON> usuariosJSON = usuarioService.findAllUsuariosOrderByNombre();
		
		return  ResponseEntity.status(HttpStatus.OK).body(usuariosJSON);
		
	}
}

