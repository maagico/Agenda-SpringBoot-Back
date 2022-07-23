package es.agenda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.agenda.json.ContactoJSON;
import es.agenda.json.CorreoJSON;
import es.agenda.json.MensajeJSON;
import es.agenda.json.TelefonoJSON;
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
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@GetMapping("/contactos/{id}")
	public ResponseEntity<ContactoJSON> contactoById(HttpServletRequest request, 
													 @PathVariable(value="id") Long idContacto){
		
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		ContactoJSON contactoJSON = contactoService.findByIdJSON(idUsuarioLogueado, idContacto);
		
		return ResponseEntity.status(HttpStatus.OK).body(contactoJSON);
	}
	
	@PutMapping("/contactos/{id}")
	public ResponseEntity<MensajeJSON> modificarContactoById(HttpServletRequest request,
															  String nombre,
															  String apellidos,
															  String telefono,
															  String segundoTelefono,
															  String correo,
															  String segundoCorreo,
													 		  @PathVariable(value="id") Long idContacto){
	
		Long idUsuarioLogueado = JWTUtils.getIdToken(request);
		
		ContactoJSON contactoJSON = new ContactoJSON();
		contactoJSON.setNombre(nombre);
		contactoJSON.setApellidos(apellidos);
		
		TelefonoJSON telefonoJSON = new TelefonoJSON(idContacto, telefono);
		TelefonoJSON segundoTelefonoJSON = new TelefonoJSON(idContacto, segundoTelefono);
		
		CorreoJSON correoJSON = new CorreoJSON(idContacto, correo);
		CorreoJSON segundoCorreoJSON = new CorreoJSON(idContacto, segundoCorreo);
		
		contactoJSON.setTelefonosJSON(List.of(telefonoJSON, segundoTelefonoJSON));
		contactoJSON.setCorreosJSON(List.of(correoJSON, segundoCorreoJSON));
		
		contactoService.modificarContactoJSON(idUsuarioLogueado, idContacto, contactoJSON);
		
		MensajeJSON mensajeJSON = new MensajeJSON();
		mensajeJSON.setOk(true);
		mensajeJSON.setTexto("Contacto modificado correctamente");
		
		return ResponseEntity.status(HttpStatus.OK).body(mensajeJSON);
	}
}