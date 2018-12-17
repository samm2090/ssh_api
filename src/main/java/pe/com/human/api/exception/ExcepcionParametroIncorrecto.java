package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ExcepcionParametroIncorrecto extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionParametroIncorrecto() {
		super();
	}

	public ExcepcionParametroIncorrecto(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionParametroIncorrecto(String message) {
		super(message);
	}

	public ExcepcionParametroIncorrecto(Throwable cause) {
		super(cause);
	}
}
