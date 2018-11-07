package pe.com.human.servicios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ExcepcionNoExisteEmpleado extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoExisteEmpleado() {
		super();
	}

	public ExcepcionNoExisteEmpleado(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoExisteEmpleado(String message) {
		super(message);
	}

	public ExcepcionNoExisteEmpleado(Throwable cause) {
		super(cause);
	}
}
