package es.agenda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.agenda.dao.UsuarioDaoI;
import es.agenda.excepcion.UsuarioYaExisteException;
import es.agenda.json.UsuarioJSON;
import es.agenda.model.Contacto;
import es.agenda.model.Usuario;

@Service("usuarioService")
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, UsuarioDaoI> implements UsuarioServiceI{
	
	
	public UsuarioServiceImpl(UsuarioDaoI dao) {
		super(dao);
	}
	
	public Usuario guardarUsuario(Usuario usuario) throws UsuarioYaExisteException {
	
		String nombreUsuario = usuario.getUsuario();
		String password = usuario.getPassword();
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		password = encoder.encode(password);
		
		usuario.setPassword(password);
		
		Boolean existeUsuario = dao.comprobarSiExisteUsuario(nombreUsuario);
		
		if(existeUsuario) {
			
			throw new UsuarioYaExisteException("Ya existe el usuario en la base de datos", null);
		}
		
		dao.persist(usuario);
		
		return usuario;
	}

	@Override
	public Usuario findByNombreUsuario(String nombreUsuarioLogueado) {
		
		return dao.findByNombreUsuario(nombreUsuarioLogueado);
	}
 
	@Override
	public List<UsuarioJSON> findAllOrderByNombreJSON() {
		
		List<Usuario> usuarios = dao.findAllUsuariosOrderByNombre();
		
		List<UsuarioJSON> usuariosJSON = 
				usuarios.stream()
				.map(u -> new UsuarioJSON(u.getId(), u.getUsuario()))
				.collect(Collectors.toList());
		
		return usuariosJSON;
	}

	@Override
	public Usuario findUsuarioByUsuarioYPassword(String usuario, String password) {
		
		return dao.findUsuarioByUsuarioYPassword(usuario, password);
	}
	
	@Override
	public String findRolesByUsuario(String usuario) {
		
		return dao.findRolesByUsuario(usuario);
	}
	
	@Override
	public List<UsuarioJSON> buscarUsuariosJSON(String textoABuscar) {
		
		textoABuscar = textoABuscar.toLowerCase();
		textoABuscar = " " + textoABuscar + " ";
		textoABuscar = textoABuscar.replace(" ", "%");
		
		List<Usuario> usuarios = dao.buscarUsuarios(textoABuscar);
		
		List<UsuarioJSON> usuariosJSON = 
				usuarios.stream()
				.map(u -> new UsuarioJSON(u.getId(), u.getUsuario()))
				.collect(Collectors.toList());
		
		return usuariosJSON;
	}

	@Override
	public List<UsuarioJSON> findAllJSON() {
		
		List<Usuario> usuarios = findAll();
		
		List<UsuarioJSON> usuariosJSON = 
				usuarios.stream()
				.map(u -> new UsuarioJSON(u.getId(), u.getUsuario()))
				.collect(Collectors.toList());
		
		return usuariosJSON;
		
	}

	@Override
	public void deleteById(Long idUsuario) {
		
		Usuario usuario = dao.findById(idUsuario);
		
		dao.remove(usuario);
	}
}
