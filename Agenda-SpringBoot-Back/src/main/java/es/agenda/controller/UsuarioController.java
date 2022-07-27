package es.agenda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.agenda.json.ContactoJSON;
import es.agenda.json.MensajeJSON;
import es.agenda.json.UsuarioJSON;
import es.agenda.service.UsuarioServiceI;
import es.agenda.util.JWTUtils;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioServiceI usuarioService;
	
	@GetMapping(value = "/usuarios")
	public ResponseEntity<List<UsuarioJSON>> getUsuarios(HttpServletRequest request,
														 String buscar) {
	
		if(buscar == null || buscar.equals("")) {
		
			List<UsuarioJSON> usuariosJSON = usuarioService.findAllOrderByNombreJSON();
		
			return  ResponseEntity.status(HttpStatus.OK).body(usuariosJSON);
		
		}else {
			
			List<UsuarioJSON> usuariosJSON = usuarioService.buscarUsuariosJSON(buscar);
			
			return  ResponseEntity.status(HttpStatus.OK).body(usuariosJSON);
		}	
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<MensajeJSON> deleteById(HttpServletRequest request, 
												  @PathVariable(value="id") Long idUsuario){
		
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		usuarioService.deleteById(idUsuario);
		
		MensajeJSON mensajeJSON = new MensajeJSON();
		mensajeJSON.setOk(true);
		mensajeJSON.setTexto("Usuario eliminado correctamente");
		
		return ResponseEntity.status(HttpStatus.OK).body(mensajeJSON);
	}
}

