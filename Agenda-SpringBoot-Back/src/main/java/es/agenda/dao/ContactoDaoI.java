package es.agenda.dao;

import java.util.List;

import es.agenda.model.Contacto;

public interface ContactoDaoI extends GenericDaoI<Contacto>{

	List<Contacto> findAllOrderByNombre(Long idUsuarioLogueado);

	List<Contacto> buscarContactos(Long idUsuarioLogueado, String textoABuscar);

	Contacto findById(Long idUsuarioLogueado, Long idContacto);

	Contacto findContactoById(Long idUsuarioLogueado, Long idContacto);
}
