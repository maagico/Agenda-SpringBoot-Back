package es.agenda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.agenda.json.ContactoJSON;
import es.agenda.service.ContactoServiceI;
import es.agenda.util.JWTUtils;

@Controller
@RequestMapping("/api")
public class ContactoController {
	
	@Autowired
	private ContactoServiceI contactoService;	

	@GetMapping("/contactos")
	public ResponseEntity<List<ContactoJSON>> listadoContactos(HttpServletRequest request) {
		
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		if(idUsuarioLogueado != null) {
		
			List<ContactoJSON> contactosJSON = contactoService.findAllOrderByNombreJSON(idUsuarioLogueado);
			
			return  ResponseEntity.status(HttpStatus.OK).body(contactosJSON);
			
		}else {
			
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@GetMapping("/contactos/{id}")
	public ResponseEntity<ContactoJSON> contactoById(HttpServletRequest request, 
													 @PathVariable(value="id") Long idContacto){
		
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		ContactoJSON contactoJSON = contactoService.findByIdJSON(idUsuarioLogueado, idContacto);
		
		return  ResponseEntity.status(HttpStatus.OK).body(contactoJSON);
	}
	
	@PutMapping("/contactos/{id}")
	public ResponseEntity<ContactoJSON> modificarContactoById(HttpServletRequest request,
															  
													 		  @PathVariable(value="id") Long idContacto){
		
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		ContactoJSON contactoJSON = contactoService.findByIdJSON(idUsuarioLogueado, idContacto);
		
		//return  ResponseEntity.status(HttpStatus.OK).body(contactoJSON);
		
		return null;
	}
}
