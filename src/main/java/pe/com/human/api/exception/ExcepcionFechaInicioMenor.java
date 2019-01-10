package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionFechaInicioMenor extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionFechaInicioMenor() {
		super();
	}

	public ExcepcionFechaInicioMenor(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionFechaInicioMenor(String message) {
		super(message);
	}

	public ExcepcionFechaInicioMenor(Throwable cause) {
		super(cause);
	}
}
