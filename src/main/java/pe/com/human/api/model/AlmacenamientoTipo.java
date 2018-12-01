package pe.com.human.api.model;

import java.io.Serializable;

public class AlmacenamientoTipo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String estado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
