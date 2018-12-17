package pe.com.human.api.exception;

//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ExcepcionContrasenaVacia extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcepcionContrasenaVacia() {
		super();
	}

	public ExcepcionContrasenaVacia(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcepcionContrasenaVacia(String message) {
		super(message);
	}

	public ExcepcionContrasenaVacia(Throwable cause) {
		super(cause);
	}
}
