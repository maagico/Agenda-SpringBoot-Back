package es.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.agenda.dao.ContactoDaoI;
import es.agenda.dao.UsuarioDaoI;
import es.agenda.json.ContactoJSON;
import es.agenda.json.CorreoJSON;
import es.agenda.json.TelefonoJSON;
import es.agenda.model.Contacto;
import es.agenda.model.Correo;
import es.agenda.model.Telefono;
import es.agenda.model.Usuario;

@Service("contactoService")
public class ContactoServiceImpl extends GenericServiceImpl<Contacto, ContactoDaoI> implements ContactoServiceI{
	
	@Autowired
	private UsuarioDaoI usuarioDao;
	
	public ContactoServiceImpl(ContactoDaoI dao) {
		super(dao);
	}

	@Override
	public List<ContactoJSON> buscarContactosJSON(Long idUsuarioLogueado, String textoABuscar) {
		
		
		textoABuscar = textoABuscar.toLowerCase();
		textoABuscar = " " + textoABuscar + " ";
		textoABuscar = textoABuscar.replace(" ", "%");
		
		List<Contacto> contactos = dao.buscarContactos(idUsuarioLogueado, textoABuscar);
		
		return convertirAContactosJSON(contactos);
	}
	
	private List<ContactoJSON> convertirAContactosJSON(List<Contacto> contactos) {
		
		List<ContactoJSON> contactosJSON = new ArrayList<>();
		
		for (Contacto contacto : contactos) {
			
			ContactoJSON contactoJSON = convertirAContactoJSON(contacto);
			
			contactosJSON.add(contactoJSON);
		}
		
		return contactosJSON;
	}
	
	private ContactoJSON convertirAContactoJSON(Contacto contacto) {
		
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
		
		return contactoJSON;
	}
	
	@Override
	public List<ContactoJSON> findAllOrderByNombreJSON(Long idUsuarioLogueado) {
		
		List<Contacto> contactos = dao.findAllOrderByNombre(idUsuarioLogueado);
	
		return convertirAContactosJSON(contactos);
	}
	
	@Override
	public ContactoJSON findByIdJSON(Long idUsuarioLogueado, Long idContacto) {
		
		Contacto contacto = dao.findById(idUsuarioLogueado, idContacto);
		
		ContactoJSON contactoJSON = convertirAContactoJSON(contacto);
		
		return contactoJSON;
	}

	@Override
	public ContactoJSON crearContactoJSON(Long idUsuarioLogueado, ContactoJSON contactoJSON) {
		
		Contacto contacto = convertirAContacto(idUsuarioLogueado, null, contactoJSON);
		
		dao.persist(contacto);
		
		Long id = contacto.getId();
		
		contactoJSON.setId(id);
		
		return contactoJSON;
	}
	
	@Override
	public ContactoJSON modificarContactoJSON(Long idUsuarioLogueado, Long idContacto, ContactoJSON contactoJSON) {
	
		Contacto contacto = convertirAContacto( idUsuarioLogueado, idContacto, contactoJSON);
		
		dao.merge(contacto);
		
		return contactoJSON;
	}
	
	private Contacto convertirAContacto(Long idUsuarioLogueado, Long idContacto, ContactoJSON contactoJSON) {
	
		String nombre = contactoJSON.getNombre();
		String apellidos = contactoJSON.getApellidos();
		
		Contacto contacto = new Contacto();
		
		if(contacto != null) {
		
			contacto.setId(idContacto);
		}
		
		Usuario usuario = usuarioDao.findById(idUsuarioLogueado);
				
		contacto.setUsuario(usuario);
		
		contacto.setNombre(nombre);
		contacto.setApellidos(apellidos);
		
		List<Telefono> telefonos = contactoJSON.getTelefonosJSON()
				.stream()
				.map(t -> new Telefono(t.getId(), t.getNumero(), contacto))
				.collect(Collectors.toList());
		
		contacto.setTelefonos(telefonos);
		
		List<Correo> correos = contactoJSON.getCorreosJSON()
				.stream()
				.map(c -> new Correo(c.getId(), c.getCorreo(), contacto))
				.collect(Collectors.toList());
		
		contacto.setCorreos(correos);
		
		return contacto;
		
	}

	@Override
	public void deleteById(Long idUsuarioLogueado, Long idContacto) {
		
		Contacto contacto = dao.findContactoById(idUsuarioLogueado, idContacto);
		
		dao.remove(contacto);
	}
}
