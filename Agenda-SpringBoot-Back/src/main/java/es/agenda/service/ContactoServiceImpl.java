package es.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import es.agenda.dao.ContactoDaoI;
import es.agenda.json.ContactoJSON;
import es.agenda.json.CorreoJSON;
import es.agenda.json.TelefonoJSON;
import es.agenda.model.Contacto;

@Service("contactoService")
public class ContactoServiceImpl extends GenericServiceImpl<Contacto, ContactoDaoI> implements ContactoServiceI{
	
	public ContactoServiceImpl(ContactoDaoI dao) {
		super(dao);
	}

	@Override
	public List<Contacto> findAllOrderByNombre(Long idUsuarioLogueado) {
		
		return dao.findAllOrderByNombre(idUsuarioLogueado);
	}

	@Override
	public List<Contacto> buscarContactos(Long idUsuarioLogueado, String textoABuscar) {
		
		textoABuscar = " " + textoABuscar + " ";
		textoABuscar = textoABuscar.replace(" ", "%");
		
		return dao.buscarContactos(idUsuarioLogueado, textoABuscar);
	}

	@Override
	public List<ContactoJSON> findAllOrderByNombreJSON(Long idUsuarioLogueado) {
		
		List<ContactoJSON> contactosJSON = new ArrayList<>();
		
		List<Contacto> contactos = dao.findAllOrderByNombre(idUsuarioLogueado);
	
		for (Contacto contacto : contactos) {
			
			ContactoJSON contactoJSON = new ContactoJSON();
			
			BeanUtils.copyProperties(contacto, contactoJSON);
			
			List<TelefonoJSON> telefonosJSON = 
					contacto.getTelefonos()
					.stream()
					.map(t -> new TelefonoJSON(t.getId(), t.getNumero()))
					.collect(Collectors.toList());
			
			contactoJSON.setTelefonosJSON(telefonosJSON);
			
			List<CorreoJSON> correosJSON = 
					contacto.getCorreos().stream()
					.map(c -> new CorreoJSON(c.getId(), c.getCorreo()))
					.collect(Collectors.toList());
			
			contactoJSON.setCorreosJSON(correosJSON);
			
			contactosJSON.add(contactoJSON);
		}
		
		return contactosJSON;
	}
	
	@Override
	public ContactoJSON findByIdJSON(Long idUsuarioLogueado, Long idContacto) {
		
		ContactoJSON contactoJSON = new ContactoJSON();
		
		Contacto contacto = dao.findById(idUsuarioLogueado, idContacto);
		
		BeanUtils.copyProperties(contacto, contactoJSON);
		
		List<TelefonoJSON> telefonosJSON = 
				contacto.getTelefonos()
				.stream()
				.map(t -> new TelefonoJSON(t.getId(), t.getNumero()))
				.collect(Collectors.toList());
		
		contactoJSON.setTelefonosJSON(telefonosJSON);
		
		List<CorreoJSON> correosJSON = 
				contacto.getCorreos().stream()
				.map(c -> new CorreoJSON(c.getId(), c.getCorreo()))
				.collect(Collectors.toList());
		
		contactoJSON.setCorreosJSON(correosJSON);
				
		return contactoJSON;
	}
}
