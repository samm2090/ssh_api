package pe.com.human.api.util;

import org.springframework.stereotype.Component;

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
		} catch (Exception e) {
			throw new ExcepcionParametroIncorrecto();
		}

		return validacion;
	}
}
