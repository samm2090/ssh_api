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

import pe.com.human.api.model.apirequest.NotificacionHumanRequest;
import pe.com.human.api.service.NotificacionService;

/**
 * @author smuroy
 *
 */
@RestController
@RequestMapping("/v1/notificacion/")
public class NotificacionController {

	@Autowired
	NotificacionService noficacionService;

	@CrossOrigin
	@RequestMapping(value = "/enviar", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> enviarNotificacion(@RequestBody NotificacionHumanRequest notificacion) {

		ResponseEntity<Map<String, Object>> respuesta = new ResponseEntity<Map<String, Object>>(
				noficacionService.enviarNotificacion(notificacion), HttpStatus.OK);

		return respuesta;
	}

}
