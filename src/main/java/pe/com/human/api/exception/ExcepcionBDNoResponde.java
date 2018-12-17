package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ExcepcionBDNoResponde extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionBDNoResponde() {
		super();
	}

	public ExcepcionBDNoResponde(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionBDNoResponde(String message) {
		super(message);
	}

	public ExcepcionBDNoResponde(Throwable cause) {
		super(cause);
	}
}
