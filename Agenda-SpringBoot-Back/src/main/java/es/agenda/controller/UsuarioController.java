package es.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public UsuarioJSON getUsuarios() {
	
		//Continuar
		usuarioService.findAllJSON();
		return null;
		
	}
}

