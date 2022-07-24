package es.agenda.json;

import lombok.Getter;
import lombok.Setter;

public class MensajeJSON {

	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private Boolean ok;
	
	@Getter
	@Setter
	private String texto;
}
