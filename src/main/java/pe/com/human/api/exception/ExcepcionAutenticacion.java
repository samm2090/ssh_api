package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ExcepcionAutenticacion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionAutenticacion() {
		super();
	}

	public ExcepcionAutenticacion(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionAutenticacion(String message) {
		super(message);
	}

	public ExcepcionAutenticacion(Throwable cause) {
		super(cause);
	}

}
