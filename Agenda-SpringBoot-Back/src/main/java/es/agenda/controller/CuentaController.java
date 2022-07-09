package es.agenda.controller;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.agenda.excepcion.UsuarioYaExisteException;
import es.agenda.json.MensajeJSON;
import es.agenda.model.Rol;
import es.agenda.model.Usuario;
import es.agenda.service.RolServiceI;
import es.agenda.service.UsuarioServiceI;

@Controller
@RequestMapping("/api")
public class CuentaController {

	@Autowired
	private UsuarioServiceI usuarioService;
	
	@Autowired
	private RolServiceI rolService;
	
	@PostMapping("/crearCuenta")
	public ResponseEntity<MensajeJSON> crearCuenta(String username, 
			   									   String password,
			   									   Long roleId) throws IllegalAccessException, InvocationTargetException {
			
		Usuario usuario = new Usuario();
		usuario.setUsuario(username);
		usuario.setPassword(password);
		
		Rol rol = rolService.findById(roleId);
		usuario.setRol(rol);
		
		MensajeJSON mensajeJSON = new MensajeJSON();
		
		try {
			
			usuarioService.guardarUsuario(usuario);
			
			mensajeJSON.setOk(true);
			mensajeJSON.setTexto("Cuenta creada correctamente");		
			
			return ResponseEntity.ok().body(mensajeJSON);
			
		}catch(UsuarioYaExisteException e){
			
			mensajeJSON.setOk(false);
			mensajeJSON.setTexto("El usuario ya existe, por favor elige otro");
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mensajeJSON);
	}		
}

