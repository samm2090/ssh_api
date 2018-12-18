package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionNoContacto extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoContacto() {
		super();
	}

	public ExcepcionNoContacto(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoContacto(String message) {
		super(message);
	}

	public ExcepcionNoContacto(Throwable cause) {
		super(cause);
	}
}
