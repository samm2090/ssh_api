package pe.com.human.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.AsistenciaDAO;
import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.BoletaDAO;
import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.dao.EvaluacionDesempenioDAO;
import pe.com.human.api.dao.PrestamoDAO;
import pe.com.human.api.dao.VacacionesDAO;
import pe.com.human.api.exception.ExcepcionNoExisteEmpleado;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apiresponse.EmpleadoResumenResponse;
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
	BaseDatosDAO baseDatosDAO;

	@Autowired
	EmpleadoDAO empleadoDAO;

	@Autowired
	BoletaDAO boletaDAO;

	@Autowired
	VacacionesDAO vacacionesDAO;

	@Autowired
	EvaluacionDesempenioDAO evaluacionDesempenioDAO;

	@Autowired
	AsistenciaDAO asistenciaDAO;

	@Autowired
	PrestamoDAO prestamoDAO;

	private static Logger logger = Logger.getLogger(EmpleadoService.class);

	static final String ROL_JEFE = "01";

	public EmpleadoResumenResponse getEmpleadoResument(EmpleadoRequest request) {
		return empleadoDAO.getEmpleadoResumen(request);
	}

	public Map<String, Object> listarCompaniasXDocumento(String documento) {
		Map<String, Object> respuesta = new HashMap<>();
		try {
			List<Map<String, Object>> companias = companiaDAO.listarCompaniasXDocumento(documento);
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
		} catch (Exception e) {
			logger.info("Exception: " + e);
		}
		return respuesta;
	}

	public Map<String, Object> authLogin(String idCompania, String idSucursal, int baseDatos, String documento,
			String contrasenia) {

		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXNombre(baseDatos);

		Map<String, Object> data = new HashMap<>();

		Empleado empleado = null;
		Compania compania = null;

		if (configuracionDataSource != null) {
			empleado = empleadoDAO.buscarEmpleadoXUsuario(idCompania, idSucursal, documento, contrasenia,
					configuracionDataSource);

			if (empleado != null) {
				compania = companiaDAO.buscarCompaniaXEmpleado(idCompania, idSucursal, empleado.getId(), contrasenia,
						configuracionDataSource);
			}
		}

		data.put("empleado", empleado);
		data.put("compania", compania);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardWidgets(String idCompania, String idSucursal, int baseDatos,
			String idEmpleado, String rol) {
		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXNombre(baseDatos);

		List<Widget> data = new ArrayList<>();

		Widget boletasWidget = boletaDAO.cantidadPagosMesActual(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget vacacionesWidget = vacacionesDAO.cantidadSaldo(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget evdWidget = evaluacionDesempenioDAO.promedioNotaDesempenio(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget asistenciaWidget = asistenciaDAO.cantidadAsistenciaMesActual(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget prestamoWidget = prestamoDAO.cantidadCuotasPendientes(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);

		data.add(boletasWidget);
		data.add(vacacionesWidget);
		data.add(evdWidget);
		data.add(asistenciaWidget);
		data.add(prestamoWidget);

		if (rol.equals(ROL_JEFE)) {
			Widget miEquipoWidget = empleadoDAO.cantidadSubordinados(idCompania, idSucursal, idEmpleado,
					configuracionDataSource);
			data.add(miEquipoWidget);
		}

		// data.put("empleado", empleado);
		// data.put("compania", compania);

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
