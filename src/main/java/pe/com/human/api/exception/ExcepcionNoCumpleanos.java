package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionNoCumpleanos extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoCumpleanos() {
		super();
	}

	public ExcepcionNoCumpleanos(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoCumpleanos(String message) {
		super(message);
	}

	public ExcepcionNoCumpleanos(Throwable cause) {
		super(cause);
	}
}
