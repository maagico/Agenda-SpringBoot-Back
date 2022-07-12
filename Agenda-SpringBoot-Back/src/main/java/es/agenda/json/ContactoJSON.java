package es.agenda.json;

import java.util.List;

import es.agenda.model.Correo;
import es.agenda.model.Telefono;
import lombok.Getter;
import lombok.Setter;

public class ContactoJSON {
	
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String nombre;
	
	@Getter
	@Setter
	private String apellidos;
	
	@Getter
	@Setter
	private List<TelefonoJSON> telefonosJSON;
	
	@Getter
	@Setter
	private List<CorreoJSON> correosJSON;
}
