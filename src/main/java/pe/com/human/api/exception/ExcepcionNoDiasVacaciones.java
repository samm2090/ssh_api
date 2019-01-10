package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionNoDiasVacaciones extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoDiasVacaciones() {
		super();
	}

	public ExcepcionNoDiasVacaciones(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoDiasVacaciones(String message) {
		super(message);
	}

	public ExcepcionNoDiasVacaciones(Throwable cause) {
		super(cause);
	}
}
