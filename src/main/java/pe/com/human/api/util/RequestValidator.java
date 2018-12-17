package pe.com.human.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import pe.com.human.api.exception.ExcepcionEspacioBlanco;
import pe.com.human.api.exception.ExcepcionParametroIncorrecto;
import pe.com.human.api.model.apirequest.EmpleadoRequest;

@Component
public class RequestValidator {

	public boolean validarEmpleadoRequest(EmpleadoRequest empleadoRequest) {
		boolean validacion = false;

		try {
			String baseDatos = empleadoRequest.getBase().getBaseDatos();
			String idCompania = empleadoRequest.getBase().getCompania().getId();
			String idSucursal = empleadoRequest.getBase().getCompania().getSucursal().getId();
			String idEmpleado = empleadoRequest.getEmpleado().getId();

			if (idCompania.isEmpty() || idSucursal.isEmpty() || baseDatos.isEmpty() || idEmpleado.isEmpty()) {
				throw new ExcepcionParametroIncorrecto();
			}
			Integer.parseInt(baseDatos);

		} catch (Exception e) {
			throw new ExcepcionParametroIncorrecto();
		}
		
		validacion = true;

		return validacion;
	}

	public boolean validarDocumento(String documento) {
		boolean validacion = false;

		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(documento);

		if (matcher.find()) {
			throw new ExcepcionEspacioBlanco();
		}else if(!documento.matches("[0-9]+")){
			
		}
		
		
		validacion = true;

		return validacion;
	}
}
