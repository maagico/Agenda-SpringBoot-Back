package es.agenda.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import es.agenda.model.Rol;
import es.agenda.model.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario> implements UsuarioDaoI{

	public UsuarioDaoImpl() {
		super(Usuario.class);
	}
	
	public Boolean comprobarSiExisteUsuario(String nombreUsuario) {
		
		String jpql = "Select U from Usuario U where U.usuario = :nombreUsuario";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nombreUsuario", nombreUsuario);
		
		Usuario usu = null;
		try {
			
			usu = (Usuario)query.getSingleResult();
		
		}catch(NoResultException e) {
			
			usu = null;
		}
		
		return usu != null;
	}

	@Override
	public Usuario findByNombreUsuario(String nombreUsuarioLogueado) {

		String jpql = "Select U from Usuario U where U.usuario = :nombreUsuarioLogueado";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nombreUsuarioLogueado", nombreUsuarioLogueado);
		
		return (Usuario)query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAllUsuariosOrderByNombre() {
		
		String jpql = "Select U from Usuario U where U.rol.rol = 'ROLE_USUARIO' order by U.usuario";
		
		Query query = entityManager.createQuery(jpql);
		
		return (List<Usuario>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarUsuarios(String textoABuscar) {
		
		String jpql = "select U from Usuario U where LOWER(U.usuario) like :textoABuscar and U.rol.rol = 'ROLE_USUARIO' order by U.usuario";
		
		Query query =  entityManager.createQuery(jpql);
		query.setParameter("textoABuscar", textoABuscar);
		
		return (List<Usuario>)query.getResultList();
	}

	@Override
	public Usuario findUsuarioByUsuarioYPassword(String usuario, String password) {
		
		String jpql = "Select U from Usuario U where U.usuario = :usuario";
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("usuario", usuario);
		
		Usuario usuarioBD = null;
		
		try {
			
			usuarioBD = (Usuario)query.getSingleResult();
			String passwordBD = usuarioBD.getPassword();
			
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Boolean iguales = bCryptPasswordEncoder.matches(password, passwordBD);
			
			if(!iguales) {
				
				usuarioBD = null;
			}
			
		}catch(NoResultException e) {
			
			usuarioBD = null;
		}
		
		return usuarioBD;
	}
	
	@Override
	public String findRolesByUsuario(String usuario) {
		
		Usuario usuarioBD = findByNombreUsuario(usuario);
		
		Rol rol = usuarioBD.getRol();
		
		return rol.getRol();
	}
}
