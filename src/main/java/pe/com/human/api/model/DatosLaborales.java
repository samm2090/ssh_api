package pe.com.human.api.model;

import java.io.Serializable;

public class DatosLaborales implements Serializable {

	private static final long serialVersionUID = 1L;
	private Texto codigo;
	private Texto puesto;
	private String rol;

	public Texto getCodigo() {
		return codigo;
	}

	public void setCodigo(Texto codigo) {
		this.codigo = codigo;
	}

	public Texto getPuesto() {
		return puesto;
	}

	public void setPuesto(Texto puesto) {
		this.puesto = puesto;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
