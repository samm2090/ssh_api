package pe.com.human.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExcepcionNoExistenBoletas extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionNoExistenBoletas() {
		super();
	}

	public ExcepcionNoExistenBoletas(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionNoExistenBoletas(String message) {
		super(message);
	}

	public ExcepcionNoExistenBoletas(Throwable cause) {
		super(cause);
	}
}
