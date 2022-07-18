package es.agenda.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

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
	
	@Setter
	private List<TelefonoJSON> telefonosJSON;
	
	@Setter
	private List<CorreoJSON> correosJSON;
	
	@JsonGetter("telefonos")
	public List<TelefonoJSON> getTelefonosJSON(){
		return telefonosJSON;
	}
	
	@JsonGetter("correos")
	public List<CorreoJSON> getCorreosJSON(){
		return correosJSON;
	}
}
