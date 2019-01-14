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
import pe.com.human.api.exception.ExcepcionCompaniaAssets;
import pe.com.human.api.exception.ExcepcionNoExisteUsuario;
import pe.com.human.api.model.Aprobador;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.DeudaMes;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.EmpleadoResumen;
import pe.com.human.api.model.Estilo;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Prestamo;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Vacaciones;
import pe.com.human.api.model.VacacionesSolicitadas;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apirequest.EmpleadoVacSolRequest;
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

					if (estilo.getAssets().isEmpty() || estilo.getColores().isEmpty()) {
						throw new ExcepcionCompaniaAssets();
					}
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

		// if (items.isEmpty()) {
		// throw new ExcepcionNoCumpleanos();
		// }

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

		// if (items.isEmpty()) {
		// throw new ExcepcionNoComunicados();
		// }

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

		// if(items.isEmpty()){
		// throw new ExcepcionNoContacto();
		// }else if(("No existe
		// dato").equals(items.get(0).getPrimeraLinea().getTexto().getTexto())){
		// throw new ExcepcionNoContacto();
		// }

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> personalNivelAcademico(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Nivel Académico");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarNivelAcademico(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> bancariaHaberes(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Cuenta Haberes");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarCuentaHaberes(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> bancariaCTS(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Cuenta CTS");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarCuentaCTS(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> bancariaPension(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("AFP/ONP");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarPension(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> seguros(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Seguros");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarSeguros(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> bienesAsignados(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Bienes Asignados");
		titulo.setEstilo(estilo);

		List<Widget> items = empleadoDAO.buscarBienesAsignados(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> dependientes(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> data = new HashMap<>();

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
		titulo.setTexto("Dependientes");
		titulo.setEstilo(estilo);

		List<Item> items = empleadoDAO.buscarDependientesXIdEmpleado(codcia, codsuc, codtra, configuracionDataSource);

		data.put("titulo", titulo);
		data.put("items", items);
		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> vacacionesDisponibles(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Vacaciones vacaciones = vacacionesDAO.resumenVacaciones(codcia, codsuc, codtra, configuracionDataSource);
		Aprobador aprobador = empleadoDAO.buscarAprobador(codcia, codsuc, codtra, configuracionDataSource);

		Map<String, Object> data = new HashMap<>();
		data.put("vacaciones", vacaciones);
		data.put("aprobador", aprobador);

		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> vacacionesSolicitudesRecientes(EmpleadoRequest empleado) {

		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String[] flgEst = { "1", "4", "5" };
		int rownum = 3;

		Map<String, Object> vacaciones = new HashMap<>();
		VacacionesSolicitadas solicitidas = vacacionesDAO.listarSolicitudVacaciones(codcia, codsuc, codtra, flgEst,
				rownum, configuracionDataSource);

		vacaciones.put("solicitadas", solicitidas);

		Map<String, Object> data = new HashMap<>();
		data.put("vacaciones", vacaciones);

		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> vacacionesSolicitudesHistorial(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String[] flgEst = { "4", "5" };
		int rownum = 100;

		Map<String, Object> vacaciones = new HashMap<>();
		VacacionesSolicitadas historial = vacacionesDAO.listarSolicitudVacacionesSimple(codcia, codsuc, codtra, flgEst,
				rownum, configuracionDataSource);

		vacaciones.put("historial", historial);

		Map<String, Object> data = new HashMap<>();
		data.put("vacaciones", vacaciones);

		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> vacacionesSolicitudesGozar(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		requestValidator.validarEmpleadoRequest(empleado);

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String[] flgEst = { "1" };
		int rownum = 100;

		Map<String, Object> vacaciones = new HashMap<>();
		VacacionesSolicitadas porGozar = vacacionesDAO.listarSolicitudVacacionesSimple(codcia, codsuc, codtra, flgEst,
				rownum, configuracionDataSource);

		vacaciones.put("porGozar", porGozar);

		Map<String, Object> data = new HashMap<>();
		data.put("vacaciones", vacaciones);

		respuesta.put("data", data);
		return respuesta;
	}

	public Map<String, Object> vacacionesSolicitudesEnviar(EmpleadoVacSolRequest empleado) {

		requestValidator.validarEmpleadoVacSolRequest(empleado);

		boolean resultado = false;
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		String categoriaVacaciones = empleado.getVacaciones().getCategoriaVacaciones();
		String fechaInicial = empleado.getVacaciones().getFechaInicial();
		String fechaFinal = empleado.getVacaciones().getFechaFinal();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		resultado = vacacionesDAO.insertarSolicitud(codcia, codsuc, codtra, categoriaVacaciones, fechaInicial,
				fechaFinal, configuracionDataSource);

		Map<String, Object> data = new HashMap<>();
		data.put("mensaje", "Se envió solicitud de vacaciones con éxito");
		respuesta.put("data", data);

		if (resultado) {
			// String ruta = "/" + codcia + "/images/Logo-THR-pequeno.png";
			// consultaEmpleado.setTipoConsulta(valorConceptualDao.find(consultaEmpleado,
			// "909"));
			// consultaEmpleado.setIdConsulta(resultado);
			// new Thread(new TareaEnvioCorreoConsultasAsincrono(empleado,
			// consultaEmpleado, ruta)).start();
		}

		return respuesta;
	}

	public Map<String, Object> prestamosBalance(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Map<String, Object> balance = new HashMap<>();
		Map<String, Object> deudaTotal = prestamoDAO.deudaTotal(codcia, codsuc, codtra, configuracionDataSource);

		balance.put("deudaTotal", deudaTotal);

		Map<String, Object> data = new HashMap<>();
		data.put("balance", balance);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> prestamosPagos(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Map<String, Object> proximos = new HashMap<>();
		List<DeudaMes> items = prestamoDAO.deudaPorMes(codcia, codsuc, codtra, configuracionDataSource);

		proximos.put("items", items);

		Map<String, Object> data = new HashMap<>();
		data.put("proximos", proximos);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> prestamosTodos(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String estado = "%";

		Map<String, Object> todos = new HashMap<>();
		List<Prestamo> items = prestamoDAO.listarPrestamos(codcia, codsuc, codtra, estado, configuracionDataSource);

		todos.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("todos", todos);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> prestamosPagados(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String estado = "1";
		Map<String, Object> pagados = new HashMap<>();
		List<Prestamo> items = prestamoDAO.listarCuotas(codcia, codsuc, codtra, estado, configuracionDataSource);

		pagados.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("pagados", pagados);
		respuesta.put("data", data);

		return respuesta;
	}

}
