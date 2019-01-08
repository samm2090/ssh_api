package pe.com.human.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.human.api.exception.ExcepcionContrasenaVacia;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apirequest.EmpleadoVacSolRequest;
import pe.com.human.api.service.EmpleadoService;

/**
 * Controller dedicado a las operaciones con la autenticacion del usuario para
 * ingresar a los sistemas.
 * 
 * @author SERGIO MUROY
 *
 */
@RestController
@RequestMapping("/v1/empleado/")
public class EmpleadoController {

	@Autowired
	EmpleadoService empleadoService;

	/**
	 * Metodo que lista todas las companias a las que pertenece un empleado por
	 * Documento Identificacion .
	 * 
	 * @param
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "{documento}/compania", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> companiaLista(@PathVariable("documento") String documento) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.listarCompaniasXDocumento(documento), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "auth/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> authLogin(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String documento = parametros.get("documento").toString();
		String contrasenia;
		try {
			contrasenia = parametros.get("contrasenia").toString();
			if (contrasenia.isEmpty()) {
				throw new ExcepcionContrasenaVacia();
			}
		} catch (Exception e) {
			throw new ExcepcionContrasenaVacia();
		}

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.authLogin(idCompania, idSucursal, baseDatos, documento, contrasenia), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/widgets", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dashboardWidgets(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();
		String rol = empleado.get("rol").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.dashboardWidgets(idCompania, idSucursal, baseDatos, idEmpleado, rol), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/pendientes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dashboardPendientes(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) compania.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.dashboardPendientes(idCompania, idSucursal, baseDatos, idEmpleado), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/birthdays", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dashboardBirthdays(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.dashboardBirthdays(idCompania, idSucursal, baseDatos), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/comunicados", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dashboardComunicados(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());

		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.dashboardComunicados(idCompania, idSucursal, baseDatos, idEmpleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "resumen", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> informacionPersonalResumen(@RequestBody EmpleadoRequest empleado) {

		Map<String, Object> respuesta = empleadoService.informacionPersonalResumen(empleado);

		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "datos/personal/informacionGeneral", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> informacionGeneral(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.informacionGeneral(idCompania, idSucursal, idEmpleado, baseDatos), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "datos/personal/datosDireccion", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> datosDireccion(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.datosDireccion(idCompania, idSucursal, idEmpleado, baseDatos), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "datos/laboral/informacionLaboral", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> informacionLaboral(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.informacionLaboral(idCompania, idSucursal, idEmpleado, baseDatos), HttpStatus.OK);

		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "datos/personal/contactoEmergencia", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> contactoEmergencia(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) parametros.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.contactoEmergencia(idCompania, idSucursal, idEmpleado, baseDatos), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/nivelAcademico", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> personalNivelAcademico(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.personalNivelAcademico(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/bancaria/haberes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> bancariaHaberes(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.bancariaHaberes(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/bancaria/cts", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> bancariaCTS(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.bancariaCTS(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/bancaria/pension", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> bancariaPension(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.bancariaPension(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/seguros", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> seguros(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.seguros(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "laboral/bienesAsignados", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> bienesAsignados(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.bienesAsignados(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "personal/dependientes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dependientes(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.dependientes(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "vacaciones/disponibles", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> vacacionesDisponibles(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.vacacionesDisponibles(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "vacaciones/solicitudes/recientes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> vacacionesSolicitudesRecientes(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.vacacionesSolicitudesRecientes(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "vacaciones/solicitudes/historial", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> vacacionesSolicitudesHistorial(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.vacacionesSolicitudesHistorial(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "vacaciones/solicitudes/gozar", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> vacacionesSolicitudesGozar(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.vacacionesSolicitudesGozar(empleado), HttpStatus.OK);

		return respuesta;
	}
	
	@CrossOrigin
	@RequestMapping(value = "vacaciones/solicitudes/enviar", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> vacacionesSolicitudesEnviar(@RequestBody EmpleadoVacSolRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.vacacionesSolicitudesEnviar(empleado), HttpStatus.OK);

		return respuesta;
	}
	
	@CrossOrigin
	@RequestMapping(value = "prestamos/balance", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> prestamosBalance(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.prestamosBalance(empleado), HttpStatus.OK);

		return respuesta;
	}
	
	@CrossOrigin
	@RequestMapping(value = "prestamos/pagos", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> prestamosPagos(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.prestamosPagos(empleado), HttpStatus.OK);

		return respuesta;
	}
	
	@CrossOrigin
	@RequestMapping(value = "prestamos/actuales", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> prestamosActuales(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.prestamosActuales(empleado), HttpStatus.OK);

		return respuesta;
	}
	
	@CrossOrigin
	@RequestMapping(value = "prestamos/pagados", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> prestamosPagados(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				empleadoService.prestamosPagados(empleado), HttpStatus.OK);

		return respuesta;
	}
}
