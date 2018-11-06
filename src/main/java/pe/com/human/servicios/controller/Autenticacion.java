package pe.com.human.servicios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.human.servicios.service.UsuarioService;

/**
 * Controller dedicado a las operaciones con la autenticacion del usuario para
 * ingresar a los sistemas.
 * 
 * @author SERGIO MUROY
 *
 */
@RestController
@RequestMapping("/v1/empleado/")
public class Autenticacion {
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Metodo que lista todas las companias a las que pertenece un empleado por
	 * Documento Identificacion	.
	 * 
	 * @param params
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "compania", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> listarCompanias(@RequestBody Map<String, String> parametros) {

		String documento = parametros.get("documento");

		return usuarioService.listarCompaniasXDocumento(documento);
	}
}
