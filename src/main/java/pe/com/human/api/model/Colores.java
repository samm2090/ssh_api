package pe.com.human.api.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class Colores implements Serializable {
	private static final long serialVersionUID = 1L;

	Color color;
	String tipo;

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

}
