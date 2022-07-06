package es.agenda.json;

import lombok.Getter;
import lombok.Setter;

public class UsuarioJSON {

	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String usuario;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private Long RoleId;
	
}
