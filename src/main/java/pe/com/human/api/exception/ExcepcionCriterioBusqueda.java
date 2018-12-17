package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionCriterioBusqueda extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionCriterioBusqueda() {
		super();
	}

	public ExcepcionCriterioBusqueda(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionCriterioBusqueda(String message) {
		super(message);
	}

	public ExcepcionCriterioBusqueda(Throwable cause) {
		super(cause);
	}

}
