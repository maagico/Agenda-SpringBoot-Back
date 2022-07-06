package es.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.agenda.json.MensajeJSON;
import es.agenda.json.UsuarioJSON;
import es.agenda.service.RolServiceI;
import es.agenda.service.UsuarioServiceI;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioServiceI usuarioService;
	
	@Autowired
	private RolServiceI rolService;
	
	
	@PostMapping(value = "usuarios")
	public MensajeJSON crearUsuarios(@RequestBody UsuarioJSON usuarioJSON) {
	
		MensajeJSON mensajeJSON = new MensajeJSON();
		
		mensajeJSON.setOk(true);
		mensajeJSON.setTexto("Usuario creado correctamente");
		
		return mensajeJSON;
		
	}
//			Usuario usuario = new Usuario();
//			BeanUtils.copyProperties(usuario, cuentaForm);
//			
//			Long roleId = cuentaForm.getRoleId();
//			Rol rol = rolService.findById(roleId);
//			usuario.setRol(rol);
//			
//			try {
//				
//				usuarioService.guardarUsuario(usuario);
//				
//			}catch(UsuarioYaExisteException e){
//				
//				String mensajeError = "El usuario ya existe, por favor elige otro";
//				
//				model.addAttribute("mensajeError", mensajeError);
//				
//				return "crearCuenta";
//			}
//			
//			return "login";
		//}		
}

