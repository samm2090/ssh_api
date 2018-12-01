package pe.com.human.api.model;

import java.io.Serializable;

public class Documento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Texto tipo;
	private Texto numeroDocumento;

	public Texto getTipo() {
		return tipo;
	}

	public void setTipo(Texto tipo) {
		this.tipo = tipo;
	}

	public Texto getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Texto numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
