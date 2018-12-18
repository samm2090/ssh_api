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

	@ExceptionHandler(ExcepcionParametroIncorrecto.class)
	protected ResponseEntity<Object> handleParametroIncorrecto(final ExcepcionParametroIncorrecto ex,
			WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.PARAMETROS_INCORRECTOS);
		error.setMensaje("Los datos enviados no son correctos");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ExcepcionEspacioBlanco.class)
	protected ResponseEntity<Object> handleEspacioBlanco(final ExcepcionEspacioBlanco ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.ESPACIO_BLANCO);
		error.setMensaje("Debe de ingresar el número de documento sin espacios en blanco");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionCaracteresNumericos.class)
	protected ResponseEntity<Object> handleCaracteresNumericos(final ExcepcionCaracteresNumericos ex,
			WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.CARACTER_NUMERICO);
		error.setMensaje("Debe de ingresar solo caracteres numéricos");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionNoExisteUsuario.class)
	protected ResponseEntity<Object> handleNoExisteUsuario(final ExcepcionNoExisteUsuario ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_USUARIO);
		error.setMensaje("No existe usuario con ese documento de identidad");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionContrasenaVacia.class)
	protected ResponseEntity<Object> handleContrasenaVacia(final ExcepcionContrasenaVacia ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_CONTRASENA);
		error.setMensaje("Debe de ingresar la contraseña");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionAutenticacion.class)
	protected ResponseEntity<Object> handleAutenticacion(final ExcepcionAutenticacion ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();
		error.setCodigo(ErrorConstantes.CREDENCIAL_INVALIDA);
		error.setMensaje("Credenciales invalidas");
		respuesta.put("error", error);

		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionCompaniaAssets.class)
	protected ResponseEntity<Object> handleCompaniaAssets(final ExcepcionCompaniaAssets ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_SELFSERVICE);
		error.setMensaje("Lo sentimos su compañía no cuenta con Selfservice Movil");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionCompaniaMenu.class)
	protected ResponseEntity<Object> handleCompaniaMenu(final ExcepcionCompaniaMenu ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_SELFSERVICE);
		error.setMensaje("Lo sentimos su compañía no cuenta con Selfservice Movil");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionCriterioBusqueda.class)
	protected ResponseEntity<Object> handleCriterioBusqueda(final ExcepcionCriterioBusqueda ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_CRITERIO);
		error.setMensaje("Debe de ingresar un criterio de busqueda");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionNoCumpleanos.class)
	protected ResponseEntity<Object> handleNoCumpleanos(final ExcepcionNoCumpleanos ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_CUMPLEANOS);
		error.setMensaje("No existen cumpleaños el día de hoy");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionNoComunicados.class)
	protected ResponseEntity<Object> handleNoComunicados(final ExcepcionNoComunicados ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_COMUNICADOS);
		error.setMensaje("No existe comunicados");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExcepcionNoContacto.class)
	protected ResponseEntity<Object> handleNoContacto(final ExcepcionNoContacto ex, WebRequest request) {
		Map<String, Object> respuesta = new HashMap<>();
		ResponseError error = new ResponseError();

		error.setCodigo(ErrorConstantes.NO_CONTACTO);
		error.setMensaje("No existe información de contacto");
		respuesta.put("error", error);
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}

}
