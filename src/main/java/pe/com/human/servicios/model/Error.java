package pe.com.human.servicios.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class Error implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String mensaje;
	private List<Error> errores;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<Error> getErrores() {
		return errores;
	}
	public void setErrores(List<Error> errores) {
		this.errores = errores;
	}
}
