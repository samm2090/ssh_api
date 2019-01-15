package pe.com.human.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import pe.com.human.api.exception.ExcepcionCaracteresNumericos;
import pe.com.human.api.exception.ExcepcionConsultaVacia;
import pe.com.human.api.exception.ExcepcionEspacioBlanco;
import pe.com.human.api.exception.ExcepcionFechaInicioMenor;
import pe.com.human.api.exception.ExcepcionFechaMenorActual;
import pe.com.human.api.exception.ExcepcionParametroIncorrecto;
import pe.com.human.api.model.apirequest.EmpleadoConsultaRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apirequest.EmpleadoVacSolRequest;

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
		} else if (!documento.matches("[0-9]+")) {
			throw new ExcepcionCaracteresNumericos();
		}

		validacion = true;

		return validacion;
	}

	public boolean validarEmpleadoVacSolRequest(EmpleadoVacSolRequest empleado) {
		boolean validacion = false;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date fechaIni = null;
		Date fechaFin = null;
		Date hoy = new Date();
		try {
			fechaIni = sdf.parse(empleado.getVacaciones().getFechaInicial());
			fechaFin = sdf.parse(empleado.getVacaciones().getFechaFinal());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new HttpMessageNotReadableException(e.getMessage());
		}

		if (fechaIni.getTime() < hoy.getTime()) {
			throw new ExcepcionFechaMenorActual();
		} else

		if (fechaIni.getTime() > fechaFin.getTime()) {
			throw new ExcepcionFechaInicioMenor();
		} else {
			validacion = true;
		}

		return validacion;
	}

	public boolean validarEmpleadoConsultaRequest(EmpleadoConsultaRequest empleado) {
		boolean validacion = false;

		String mensaje = empleado.getConsulta().getMensaje();

		if (mensaje == null || ("").equals(mensaje)) {
			throw new ExcepcionConsultaVacia();
		} else {
			validacion = true;
		}

		return validacion;
	}
}
