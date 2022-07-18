package es.agenda.service;

import java.util.List;

import es.agenda.json.ContactoJSON;
import es.agenda.model.Contacto;

public interface ContactoServiceI extends GenericServiceI<Contacto>{

	List<Contacto> findAllOrderByNombre(Long idToken);

	List<Contacto> buscarContactos(Long idToken, String textoABuscar);

	ContactoJSON findByIdJSON(Long idToken, Long id);
	
	List<ContactoJSON> findAllOrderByNombreJSON(Long idToken);
}
