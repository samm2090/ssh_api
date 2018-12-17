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

import pe.com.human.api.model.apirequest.DirectorioRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.service.CompaniaService;

/**
 * 
 * @author smuroy
 *
 */
@RestController
@RequestMapping("/v1/compania/")
public class CompaniaController {

	@Autowired
	CompaniaService companiaService;
	
	@CrossOrigin
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> estilos(@PathVariable("id") int id) {

		return new ResponseEntity<Map<String, Object>>(
				companiaService.estiloCompania(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "directorio/area", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> directorioArea(@RequestBody EmpleadoRequest empleado) {

		return new ResponseEntity<Map<String, Object>>(
				companiaService.directorioArea(empleado), HttpStatus.OK);
	}
	
	@RequestMapping(value = "directorio/busqueda", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> directorioBusqueda(@RequestBody DirectorioRequest directorio) {

		return new ResponseEntity<Map<String, Object>>(
				companiaService.directorioBusqueda(directorio), HttpStatus.OK);
	}
	
	
}
