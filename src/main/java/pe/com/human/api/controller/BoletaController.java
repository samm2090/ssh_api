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

import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.service.BoletaService;

/**
 * 
 * @author smuroy
 *
 */

@RestController
@RequestMapping("/v1/boleta/")
public class BoletaController {

	@Autowired
	BoletaService boletaService;

	@CrossOrigin
	@RequestMapping(value = "empleado", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> empleado(@RequestBody EmpleadoRequest empleado) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				boletaService.buscarBoletasXEmpleado(empleado), HttpStatus.OK);

		return respuesta;
	}

}
