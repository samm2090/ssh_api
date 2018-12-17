package pe.com.human.api.model;

import java.io.Serializable;

public class Default implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;

	public Default() {
	}

	public Default(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
