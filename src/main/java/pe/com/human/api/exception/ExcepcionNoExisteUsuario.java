package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ExcepcionNoExisteUsuario extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoExisteUsuario() {
		super();
	}

	public ExcepcionNoExisteUsuario(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoExisteUsuario(String message) {
		super(message);
	}

	public ExcepcionNoExisteUsuario(Throwable cause) {
		super(cause);
	}
}
