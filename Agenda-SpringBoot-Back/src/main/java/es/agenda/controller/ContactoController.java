package es.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.agenda.json.ContactoJSON;
import es.agenda.service.ContactoServiceI;

@Controller
@RequestMapping("/api")
public class ContactoController {
	
	@Autowired
	private ContactoServiceI contactoService;	

	@GetMapping("contactos")
	public ResponseEntity<List<ContactoJSON>> listadoContactos() {
		
		Long id = 5L; //Recuperar del token 
		
		List<ContactoJSON> contactosJSON = contactoService.findAllOrderByNombreJSON(id);
		
		return  ResponseEntity.status(HttpStatus.OK).body(contactosJSON);
	}
	
	@GetMapping("/contactos/{id}")
	public ResponseEntity<ContactoJSON> contactoById(@PathVariable(value="id") Long id){
		
		ContactoJSON contactoJSON = new ContactoJSON();
		
		contactoService.findByIdJSON(null);
		
		return  ResponseEntity.status(HttpStatus.OK).body(contactoJSON);
	}
}
