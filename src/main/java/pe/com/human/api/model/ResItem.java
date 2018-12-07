package pe.com.human.api.model;

import java.io.Serializable;

public class ResItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tipo;
	private Archivo archivo;
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

}
