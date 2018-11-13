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

import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apiresponse.EmpleadoResumenResponse;
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
public class EmpleadoController{

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
		return new ResponseEntity<Map<String, Object>>(empleadoService.listarCompaniasXDocumento(documento),
				HttpStatus.OK);
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
		String contrasenia = parametros.get("contrasenia").toString();

		return new ResponseEntity<Map<String, Object>>(
				empleadoService.authLogin(idCompania, idSucursal, baseDatos, documento, contrasenia), HttpStatus.OK);
	}

	@RequestMapping(value = "resumen", method = RequestMethod.POST)
	public ResponseEntity<EmpleadoResumenResponse> empleadoResumen(@RequestBody EmpleadoRequest request) {
		EmpleadoResumenResponse response = empleadoService.getEmpleadoResument(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/widgets", method = RequestMethod.POST)
	public Map<String, Object> dashboardWidgets(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) compania.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		int baseDatos = Integer.parseInt(base.get("baseDatos").toString());
		String idEmpleado = empleado.get("id").toString();
		String rol = empleado.get("rol").toString();

		return empleadoService.dashboardWidgets(idCompania, idSucursal, baseDatos, idEmpleado, rol);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/pendientes", method = RequestMethod.POST)
	public Map<String, Object> dashboardPendientes(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) compania.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		String baseDatos = base.get("baseDatos").toString();
		String idEmpleado = empleado.get("id").toString();

		return empleadoService.dashboardPendientes(idCompania, idSucursal, baseDatos, idEmpleado);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/birthdays", method = RequestMethod.POST)
	public Map<String, Object> dashboardBirthdays(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		String baseDatos = base.get("baseDatos").toString();

		String mes = parametros.get("mes").toString();

		return empleadoService.dashboardBirthdays(idCompania, idSucursal, baseDatos, mes);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "dashboard/comunicados", method = RequestMethod.POST)
	public Map<String, Object> dashboardComunicados(@RequestBody Map<String, Object> parametros) {
		Map<String, Object> base = (Map<String, Object>) parametros.get("base");
		Map<String, Object> compania = (Map<String, Object>) base.get("compania");
		Map<String, Object> sucursal = (Map<String, Object>) compania.get("sucursal");
		Map<String, Object> empleado = (Map<String, Object>) compania.get("empleado");

		String idCompania = compania.get("id").toString();
		String idSucursal = sucursal.get("id").toString();
		String baseDatos = base.get("baseDatos").toString();

		String idEmpleado = empleado.get("id").toString();

		return empleadoService.dashboardComunicados(idCompania, idSucursal, baseDatos, idEmpleado);
	}

}
