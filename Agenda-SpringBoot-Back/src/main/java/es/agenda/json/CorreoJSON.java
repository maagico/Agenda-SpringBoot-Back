package es.agenda.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CorreoJSON {

	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String correo;
}
