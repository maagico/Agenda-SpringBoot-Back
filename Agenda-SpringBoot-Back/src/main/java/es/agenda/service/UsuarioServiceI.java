package es.agenda.service;

import java.util.List;

import es.agenda.excepcion.UsuarioYaExisteException;
import es.agenda.json.UsuarioJSON;
import es.agenda.model.Usuario;

public interface UsuarioServiceI extends GenericServiceI<Usuario>{
	
	Usuario guardarUsuario(Usuario usuario) throws UsuarioYaExisteException;

	Usuario findByNombreUsuario(String nombreUsuarioLogueado);

	List<UsuarioJSON> findAllOrderByNombreJSON();

	List<UsuarioJSON> buscarUsuariosJSON(String textoABuscar);

	Usuario findUsuarioByUsuarioYPassword(String usuario, String password);

	String findRolesByUsuario(String usuario);

	List<UsuarioJSON> findAllJSON();

	void deleteById(Long idUsuario);
}
