package pe.com.human.api.model;

import java.io.Serializable;

/**
 * @author smuroy
 *
 */
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String rol;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}
