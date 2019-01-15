package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionConsultaVacia extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionConsultaVacia() {
		super();
	}

	public ExcepcionConsultaVacia(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionConsultaVacia(String message) {
		super(message);
	}

	public ExcepcionConsultaVacia(Throwable cause) {
		super(cause);
	}
}