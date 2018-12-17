package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionEspacioBlanco extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionEspacioBlanco() {
		super();
	}

	public ExcepcionEspacioBlanco(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionEspacioBlanco(String message) {
		super(message);
	}

	public ExcepcionEspacioBlanco(Throwable cause) {
		super(cause);
	}
}
