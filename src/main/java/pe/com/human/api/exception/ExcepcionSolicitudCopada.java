package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionSolicitudCopada extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionSolicitudCopada() {
		super();
	}

	public ExcepcionSolicitudCopada(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionSolicitudCopada(String message) {
		super(message);
	}

	public ExcepcionSolicitudCopada(Throwable cause) {
		super(cause);
	}
}
