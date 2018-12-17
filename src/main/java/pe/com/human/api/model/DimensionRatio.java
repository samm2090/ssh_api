package pe.com.human.api.model;

import java.io.Serializable;

public class DimensionRatio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String radio;
	private String ancho;
	private String alto;

	public DimensionRatio() {

	}

	public DimensionRatio(String radio, String ancho, String alto) {
		this.radio = radio;
		this.ancho = ancho;
		this.alto = alto;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}

	public String getAlto() {
		return alto;
	}

	public void setAlto(String alto) {
		this.alto = alto;
	}

}
