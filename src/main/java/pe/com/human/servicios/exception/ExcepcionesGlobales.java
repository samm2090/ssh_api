package pe.com.human.servicios.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.human.servicios.util.ResponseError;

@ControllerAdvice
public class ExcepcionesGlobales extends ResponseEntityExceptionHandler {

	public ExcepcionesGlobales() {
	}

	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");

		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(error);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
	}
	

	
	// @ExceptionHandler({ NoHandlerFoundException.class })
	// @ResponseStatus(value = HttpStatus.NOT_FOUND)
	// @ResponseBody
	// public ResponseEntity<ResponseError> req(HttpServletRequest req,
	// NoHandlerFoundException ex) {
	//
	// ResponseError error = new ResponseError();
	//
	// error.setCodigo(HttpStatus.NOT_FOUND.toString());
	// error.setMensaje("Porfin");
	// return new ResponseEntity<ResponseError>(error, HttpStatus.BAD_REQUEST);
	// }
	//

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");


		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	// @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	// public Map<String, Object> handleError405(HttpServletRequest request,
	// Exception e) {
	//
	// ResponseError error = new ResponseError();
	//
	// error.setCodigo(HttpStatus.NOT_FOUND.toString());
	// error.setMensaje("Porfin");
	//
	// Map<String, Object> respuesta = new HashMap<>();
	// respuesta.put("error", error);
	// return respuesta;
	// }

	// 400

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");

		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(error);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleBadRequest(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");


		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ ExcepcionNoExisteEmpleado.class })
	protected ResponseEntity<Object> prueba(final ExcepcionNoExisteEmpleado ex, WebRequest request) {

		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");

		return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	


	// @Override
	// protected ResponseEntity<Object> handleHttpMessageNotReadable(final
	// HttpMessageNotReadableException ex,
	// final HttpHeaders headers, final HttpStatus status, final WebRequest
	// request) {
	// final String bodyOfResponse = "This should be application specific";
	// // ex.getCause() instanceof JsonMappingException, JsonParseException //
	// // for additional information later on
	// return handleExceptionInternal(ex, bodyOfResponse, headers,
	// HttpStatus.BAD_REQUEST, request);
	// }
	//
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		ResponseError error = new ResponseError();

		error.setCodigo(HttpStatus.NOT_FOUND.toString());
		error.setMensaje("Porfin");

		ObjectMapper mapper = new ObjectMapper();

		String json = "";
		try {
			json = mapper.writeValueAsString(error);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
	}

	// 403
	// @ExceptionHandler({ AccessDeniedException.class })
	// public ResponseEntity<Object> handleAccessDeniedException(final Exception
	// ex, final WebRequest request) {
	// System.out.println("request" + request.getUserPrincipal());
	// return new ResponseEntity<Object>("Access denied message here", new
	// HttpHeaders(), HttpStatus.FORBIDDEN);
	// }

	// 409

	// @ExceptionHandler({ InvalidDataAccessApiUsageException.class,
	// DataAccessException.class })
	// protected ResponseEntity<Object> handleConflict(final RuntimeException
	// ex, final WebRequest request) {
	// final String bodyOfResponse = "This should be application specific";
	// return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
	// HttpStatus.CONFLICT, request);
	// }

	// 412

	// 500

	// @ExceptionHandler({ NullPointerException.class,
	// IllegalArgumentException.class, IllegalStateException.class })
	// /* 500 */public ResponseEntity<Object> handleInternal(final
	// RuntimeException ex, final WebRequest request) {
	// logger.error("500 Status Code", ex);
	// final String bodyOfResponse = "This should be application specific";
	// return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
	// HttpStatus.INTERNAL_SERVER_ERROR,
	// request);
	// }
}
