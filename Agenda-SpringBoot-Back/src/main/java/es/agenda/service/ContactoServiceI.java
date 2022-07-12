package es.agenda.service;

import java.util.List;

import es.agenda.json.ContactoJSON;
import es.agenda.model.Contacto;

public interface ContactoServiceI extends GenericServiceI<Contacto>{

	List<Contacto> findAllOrderByNombre(Long id);

	List<Contacto> buscarContactos(Long id, String textoABuscar);

	ContactoJSON findByIdJSON(Long id);
	
	List<ContactoJSON> findAllOrderByNombreJSON(Long id);
}
