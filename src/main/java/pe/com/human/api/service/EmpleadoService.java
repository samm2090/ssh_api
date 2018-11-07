package pe.com.human.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.exception.ExcepcionNoExisteEmpleado;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Empleado;
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

	@Autowired
	EmpleadoDAO empleadoDAO;

	public Map<String, Object> listarCompaniasXDocumento(String documento) {
		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource conf = new ConfiguracionDataSource();

		List<Map<String, Object>> companias = new ArrayList<>();
		List<ConfiguracionDataSource> configuraciones = conf.listarConfiguracionJson("/bases_datos.json");

		for (ConfiguracionDataSource configuracion : configuraciones) {
			companias.addAll(companiaDAO.listarCompaniasXDocumento(documento, configuracion));
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

		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource("/bases_datos.json", baseDatos);

		Map<String, Object> data = new HashMap<>();

		Empleado empleado = empleadoDAO.buscarEmpleadoXUsuario(idCompania, idSucursal, documento, contrasenia,
				configuracionDataSource);

		Compania compania = companiaDAO.buscarCompaniaXEmpleado(idCompania, idSucursal, empleado.getId(), contrasenia,
				configuracionDataSource);

		data.put("empleado", empleado);
		data.put("compania", compania);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardWidgets(String idCompania, String idSucursal, String baseDatos,
			String idEmpleado, String rol) {
		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource("/bases_datos.json", baseDatos);

		Map<String, Object> data = new HashMap<>();


//		data.put("empleado", empleado);
//		data.put("compania", compania);

		respuesta.put("data", data);

		return respuesta;
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
