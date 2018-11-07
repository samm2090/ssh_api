package pe.com.human.api.util;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class ResponseError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String mensaje;
	private List<ResponseError> errores;
	
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
	public List<ResponseError> getErrores() {
		return errores;
	}
	public void setErrores(List<ResponseError> errores) {
		this.errores = errores;
	}
}
