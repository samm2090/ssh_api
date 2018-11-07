package pe.com.human.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.dao.UsuarioDAO;
import pe.com.human.api.exception.ExcepcionNoExisteEmpleado;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Service
public class EmpleadoService {

	@Autowired
	CompaniaDAO companiaDAO;
	
	public Map<String, Object> listarCompaniasXDocumento(String documento) {
		Map<String, Object> respuesta = new HashMap<>();

		ObjectMapper mapper = new ObjectMapper();

		List<Map<String, Object>> companias = new ArrayList<>();
		try {
			List<ConfiguracionDataSource> configuraciones = mapper.readValue(
					new File(getClass().getResource("/bases_datos.json").getPath()),
					new TypeReference<List<ConfiguracionDataSource>>() {
					});

			for (ConfiguracionDataSource configuracion : configuraciones) {
				companias.addAll(companiaDAO.listarCompaniasXDocumento(documento, configuracion));
			}

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!companias.isEmpty()) {

			List<Map<String, Object>> data = new ArrayList<>();
			Map<String, Object> comp;
			for (Map<String, Object> compania : companias) {
				comp = new HashMap<>();
				comp.put("compania", compania.get("compania"));
				comp.put("baseDatos", compania.get("baseDatos"));
				data.add(comp);
			}

			respuesta.put("data", data);
		} else {
			throw new ExcepcionNoExisteEmpleado();
		}

		return respuesta;
	}

	public Map<String, Object> authLogin(String idCompania, String idSucursal, String baseDatos, String documento,
			String contrasenia) {
		
		
		return null;
	}

	public Map<String, Object> dashboardWidgets(String idCompania, String idSucursal, String baseDatos,
			String idEmpleado, String rol) {
		return null;
	}

	public Map<String, Object> dashboardPendientes(String idCompania, String idSucursal, String baseDatos,
			String idEmpleado) {
		return null;
	}

	public Map<String, Object> dashboardBirthdays(String idCompania, String idSucursal, String baseDatos, String mes) {
		return null;
	}

	public Map<String, Object> dashboardComunicados(String idCompania, String idSucursal, String baseDatos,
			String idEmpleado) {
		return null;
	}

}
