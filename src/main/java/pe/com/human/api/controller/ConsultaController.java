package pe.com.human.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.human.api.model.apirequest.EmpleadoConsultaRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.service.ConsultaService;

@RestController
@RequestMapping("/v1/consulta/")
public class ConsultaController {

	@Autowired
	ConsultaService consultaService;

	@CrossOrigin
	@RequestMapping(value = "recientes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> recientes(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.listarRecientes(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "tipos", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> tipos(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.listarTipos(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "historial", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> historial(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.listarHistorial(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "pendientes", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> pendientes(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.listarPendientes(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "detalle", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> detalle(@RequestBody EmpleadoConsultaRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.detalle(empleado), HttpStatus.OK);

		return respuesta;
	}

	@CrossOrigin
	@RequestMapping(value = "enviar", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> enviarConsulta(@RequestBody EmpleadoConsultaRequest empleado) {
		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				consultaService.enviar(empleado), HttpStatus.OK);

		return respuesta;
	}
}
