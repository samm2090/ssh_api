package pe.com.human.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.AsistenciaDAO;
import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.BoletaDAO;
import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.dao.EstiloDAO;
import pe.com.human.api.dao.EvaluacionDesempenioDAO;
import pe.com.human.api.dao.PrestamoDAO;
import pe.com.human.api.dao.VacacionesDAO;
import pe.com.human.api.exception.ExcepcionAutenticacion;
import pe.com.human.api.exception.ExcepcionNoComunicados;
import pe.com.human.api.exception.ExcepcionNoExisteUsuario;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.EmpleadoResumen;
import pe.com.human.api.model.Estilo;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.RequestValidator;

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
	EstiloDAO estiloDAO;

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

	@Autowired
	RequestValidator requestValidator;

	// private static Logger logger = Logger.getLogger(EmpleadoService.class);

	static final String ROL_JEFE = "1";

	public Map<String, Object> listarCompaniasXDocumento(String documento) {
		requestValidator.validarDocumento(documento);

		Map<String, Object> respuesta = new HashMap<>();
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
			throw new ExcepcionNoExisteUsuario();
		}
		return respuesta;
	}

	public Map<String, Object> authLogin(String idCompania, String idSucursal, int baseDatos, String documento,
			String contrasenia) {

		Map<String, Object> respuesta = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Map<String, Object> data = new HashMap<>();

		Empleado empleado = null;
		Compania compania = null;

		if (configuracionDataSource != null) {
			empleado = empleadoDAO.buscarEmpleadoXUsuario(idCompania, idSucursal, documento, contrasenia,
					configuracionDataSource);

			if (empleado != null) {
				compania = companiaDAO.buscarCompaniaXEmpleado(idCompania, idSucursal, empleado.getId(), contrasenia,
						configuracionDataSource);

				if (compania != null) {
					ConfiguracionDataSource configBaseAppMovil = new ConfiguracionDataSource();

					Estilo estilo = estiloDAO.buscarEstiloXCompania(compania, configBaseAppMovil);
					compania.setEstilo(estilo);
				}
			} else {
				throw new ExcepcionAutenticacion();
			}
		}

		data.put("compania", compania);
		data.put("empleado", empleado);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardWidgets(String idCompania, String idSucursal, int baseDatos, String idEmpleado,
			String rol) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		List<Widget> widgets = new ArrayList<>();

		Widget boletasWidget = boletaDAO.cantidadBoletasNoLeidas(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget vacacionesWidget = vacacionesDAO.cantidadSaldo(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget evdWidget = evaluacionDesempenioDAO.promedioNotaDesempenio(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget asistenciaWidget = asistenciaDAO.cantidadAsistenciaMesActual(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		Widget prestamoWidget = prestamoDAO.cantidadCuotasPendientes(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);

		if (rol.equals(ROL_JEFE)) {
			Widget miEquipoWidget = empleadoDAO.cantidadSubordinados(idCompania, idSucursal, idEmpleado,
					configuracionDataSource);
			if (miEquipoWidget != null)
				widgets.add(miEquipoWidget);
		}

		if (boletasWidget != null)
			widgets.add(boletasWidget);
		if (vacacionesWidget != null)
			widgets.add(vacacionesWidget);
		if (evdWidget != null)
			widgets.add(evdWidget);
		if (asistenciaWidget != null)
			widgets.add(asistenciaWidget);
		if (prestamoWidget != null)
			widgets.add(prestamoWidget);

		data.put("widgets", widgets);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardPendientes(String idCompania, String idSucursal, int baseDatos,
			String idEmpleado) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("SECONDARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Comunicados");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.listarCumpleanos(idCompania, idSucursal, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardBirthdays(String idCompania, String idSucursal, int baseDatos) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("SECONDARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Hoy Celebramos Cumpleaños");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.listarCumpleanos(idCompania, idSucursal, configuracionDataSource);

//		if (items.isEmpty()) {
//			throw new ExcepcionNoCumpleanos();
//		}

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> dashboardComunicados(String idCompania, String idSucursal, int baseDatos,
			String idEmpleado) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("SECONDARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Comunicados");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.listarFeriados(idCompania, idSucursal, configuracionDataSource);

//		if (items.isEmpty()) {
//			throw new ExcepcionNoComunicados();
//		}

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> informacionPersonalResumen(EmpleadoRequest empleadoRequest) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleadoRequest);

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO
				.buscarConfiguracionXId(Integer.parseInt(empleadoRequest.getBase().getBaseDatos()));
		Map<String, Object> data = new HashMap<>();

		EmpleadoResumen empleado = empleadoDAO.buscarEmpleadoResumen(empleadoRequest, configuracionDataSource);

		data.put("empleado", empleado);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> informacionGeneral(String idCompania, String idSucursal, String idEmpleado,
			int baseDatos) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("PRIMARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Información General");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarInformacionGeneral(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> datosDireccion(String idCompania, String idSucursal, String idEmpleado, int baseDatos) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("PRIMARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Datos Dirección");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarDatosDireccion(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> informacionLaboral(String idCompania, String idSucursal, String idEmpleado,
			int baseDatos) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("PRIMARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Información Laboral");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarInformacionLaboral(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> contactoEmergencia(String idCompania, String idSucursal, String idEmpleado,
			int baseDatos) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Default default1 = new Default();
		default1.setNombre("PRIMARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Contacto de Emergencia");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarDatosEmergencia(idCompania, idSucursal, idEmpleado,
				configuracionDataSource);
		
//		if(items.isEmpty()){
//			throw new ExcepcionNoContacto();
//		}else if(("No existe dato").equals(items.get(0).getPrimeraLinea().getTexto().getTexto())){
//			throw new ExcepcionNoContacto();
//		}

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}
}
