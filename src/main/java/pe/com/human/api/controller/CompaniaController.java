package pe.com.human.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
}
