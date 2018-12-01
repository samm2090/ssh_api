package pe.com.human.api.model;

import java.io.Serializable;

public class Texto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String text;
	private EstiloTexto estilo;

	public Texto(String text, EstiloTexto estilo) {
		this.text = text;
		this.estilo = estilo;
	}

	public Texto() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EstiloTexto getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloTexto estilo) {
		this.estilo = estilo;
	}

}
