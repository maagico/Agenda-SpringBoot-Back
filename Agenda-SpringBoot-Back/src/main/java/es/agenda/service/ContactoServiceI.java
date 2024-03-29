package es.agenda.service;

import java.util.List;

import es.agenda.json.ContactoJSON;
import es.agenda.model.Contacto;

public interface ContactoServiceI extends GenericServiceI<Contacto>{

	List<ContactoJSON> buscarContactosJSON(Long idToken, String textoABuscar);

	ContactoJSON findByIdJSON(Long idToken, Long id);
	
	List<ContactoJSON> findAllOrderByNombreJSON(Long idToken);

	ContactoJSON modificarContactoJSON(Long idUsuarioLogueado, Long idContacto, ContactoJSON contactoJSON);

	void deleteById(Long idUsuarioLogueado, Long idContacto);

	ContactoJSON crearContactoJSON(Long idUsuarioLogueado, ContactoJSON contactoJSON);
}
