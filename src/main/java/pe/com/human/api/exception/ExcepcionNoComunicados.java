package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionNoComunicados extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoComunicados() {
		super();
	}

	public ExcepcionNoComunicados(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoComunicados(String message) {
		super(message);
	}

	public ExcepcionNoComunicados(Throwable cause) {
		super(cause);
	}
}
