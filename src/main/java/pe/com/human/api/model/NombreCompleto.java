package pe.com.human.api.model;

import java.io.Serializable;

public class NombreCompleto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Texto nombre;
	private Texto apePaterno;
	private Texto apeMaterno;

	public Texto getNombre() {
		return nombre;
	}

	public void setNombre(Texto nombre) {
		this.nombre = nombre;
	}

	public Texto getApePaterno() {
		return apePaterno;
	}

	public void setApePaterno(Texto apePaterno) {
		this.apePaterno = apePaterno;
	}

	public Texto getApeMaterno() {
		return apeMaterno;
	}

	public void setApeMaterno(Texto apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

}
