package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class ExcepcionCompaniaMenu extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionCompaniaMenu() {
		super();
	}

	public ExcepcionCompaniaMenu(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionCompaniaMenu(String message) {
		super(message);
	}

	public ExcepcionCompaniaMenu(Throwable cause) {
		super(cause);
	}
}
