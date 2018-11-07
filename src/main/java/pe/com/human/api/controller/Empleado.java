package pe.com.human.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class Empleado {

	@Autowired
	EmpleadoService usuarioService;

	@RequestMapping(value = "{documento}/compania", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> companiaLista(@PathVariable("documento") String documento) {
		Map<String, Object> compania = usuarioService.listarCompaniasXDocumento(String.valueOf(documento));
		return new ResponseEntity<Map<String, Object>>(compania, HttpStatus.OK);
	}

}
