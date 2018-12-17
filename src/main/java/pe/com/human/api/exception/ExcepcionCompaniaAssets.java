package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionCompaniaAssets extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionCompaniaAssets() {
		super();
	}

	public ExcepcionCompaniaAssets(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionCompaniaAssets(String message) {
		super(message);
	}

	public ExcepcionCompaniaAssets(Throwable cause) {
		super(cause);
	}
}
