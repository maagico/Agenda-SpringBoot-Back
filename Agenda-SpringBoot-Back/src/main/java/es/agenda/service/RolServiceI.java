package es.agenda.service;

import java.util.List;

import es.agenda.json.RolJSON;
import es.agenda.model.Rol;

public interface RolServiceI extends GenericServiceI<Rol>{

	List<RolJSON> findAllJSON();
}
