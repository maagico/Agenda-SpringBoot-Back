package es.agenda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import es.agenda.dao.RolDaoI;
import es.agenda.json.RolJSON;
import es.agenda.model.Rol;

@Service("rolService")
public class RolServiceImpl extends GenericServiceImpl<Rol, RolDaoI> implements RolServiceI{
	
	public RolServiceImpl(RolDaoI dao) {
		super(dao);
	}

	@Override
	public List<RolJSON> findAllJSON() {
		
		List<Rol> roles = findAll();
		
		List<RolJSON> rolesJSON = convierteListRolAListRolJSON(roles);
		
		return rolesJSON;
	}

	private List<RolJSON> convierteListRolAListRolJSON(List<Rol> roles) {
		
		List<RolJSON> rolesJSON = new ArrayList<>();
		
		for (Rol rol : roles) {
		
			RolJSON rolJSON = new RolJSON();
			
			BeanUtils.copyProperties(rol, rolJSON);
			
			rolesJSON.add(rolJSON);
		}
		
		return rolesJSON;
	}	
}
