package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionFechaMenorActual extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionFechaMenorActual() {
		super();
	}

	public ExcepcionFechaMenorActual(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionFechaMenorActual(String message) {
		super(message);
	}

	public ExcepcionFechaMenorActual(Throwable cause) {
		super(cause);
	}
}
