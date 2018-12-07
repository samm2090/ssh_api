package pe.com.human.api.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class EstiloTexto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fuente fuente;
	private Color color;
	private Custom custom;
	
	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
