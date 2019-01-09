package pe.com.human.api.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class Texto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String texto;
	private EstiloTexto estilo;

	public Texto(String texto, EstiloTexto estilo) {
		this.texto = texto;
		this.estilo = estilo;
	}

	public Texto(String texto) {
		this.texto = texto;
	}

	public Texto() {
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public EstiloTexto getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloTexto estilo) {
		this.estilo = estilo;
	}

}
