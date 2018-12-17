package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionCaracteresNumericos extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionCaracteresNumericos() {
		super();
	}

	public ExcepcionCaracteresNumericos(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionCaracteresNumericos(String message) {
		super(message);
	}

	public ExcepcionCaracteresNumericos(Throwable cause) {
		super(cause);
	}
}
