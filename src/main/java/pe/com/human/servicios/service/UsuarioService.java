package pe.com.human.servicios.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.servicios.dao.CompaniaDAO;
import pe.com.human.servicios.model.Compania;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Service
public class UsuarioService {

	@Autowired
	CompaniaDAO companiaDAO;

	public List<Map<String, Object>> listarCompaniasXDocumento(String documento) {
		List<Map<String, Object>> respuesta = new ArrayList<>();

		List<Compania> companias = companiaDAO.listarCompaniasXDocumento(documento);
		
		for(Compania compania : companias){
			Map<String, Object> data = new HashMap<>();
			data.put("compania", compania);
			data.put("baseDatos", "payrollqa");
			respuesta.add(data);
		}		

		return respuesta;
	}

}
