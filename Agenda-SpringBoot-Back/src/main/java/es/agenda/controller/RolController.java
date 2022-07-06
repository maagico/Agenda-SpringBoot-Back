package es.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.agenda.json.RolJSON;
import es.agenda.service.RolServiceI;

@RestController
@RequestMapping("/api")
public class RolController {

	@Autowired
	private RolServiceI rolService;

	@GetMapping(value = "/roles")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<RolJSON> getRoles(){
		
		return rolService.findAllJSON(); 
	}
}
