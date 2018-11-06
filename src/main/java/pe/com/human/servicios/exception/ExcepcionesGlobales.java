package pe.com.human.servicios.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class ExcepcionesGlobales extends ResponseEntityExceptionHandler {

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	public  defaultExceptionHandler(HttpMessageNotReadableException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//
//
//		  ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
//			        .withStatus(status)
//			        .withError_code(status.BAD_REQUEST.name())
//			        .withMessage(ex.getLocalizedMessage()).build();

		
//		Error error = new Error();
//		Map<String, Object> respuesta = new HashMap<>();
//		respuesta.put("error", error);

//		return new ResponseEntity<>(response, response.getStatus());
//
//	}

}
