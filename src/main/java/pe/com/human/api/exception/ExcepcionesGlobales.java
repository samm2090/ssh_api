package pe.com.human.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pe.com.human.api.constants.ErrorConstantes;
import pe.com.human.api.util.ResponseError;

@RestControllerAdvice
public class ExcepcionesGlobales extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ExcepcionBDNoResponde.class)
	protected ResponseEntity<Object> handelBDNoResponde(final ExcepcionBDNoResponde ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();

		ResponseError error = new ResponseError();
		error.setCodigo(ErrorConstantes.BASE_DATOS);
		error.setMensaje("Se produjo un error en la conexión con la base de datos");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.SERVICIO_NO_DISPONILBLE);
		error.setMensaje("El servicio no se encuentra disponible");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.PARAMETROS_INCORRECTOS);
		error.setMensaje("Los datos enviados no son correctos");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ExcepcionNoExisteUsuario.class)
	protected ResponseEntity<Object> handleNoExisteUsuario(final ExcepcionNoExisteUsuario ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ExcepcionParametroIncorrecto.class)
	protected ResponseEntity<Object> handleParametroIncorrecto(final ExcepcionParametroIncorrecto ex,
			WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.UNPROCESSABLE_ENTITY.toString());
		error.setMensaje("Los datos enviados no son correctos");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
