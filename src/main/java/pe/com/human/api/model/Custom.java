package pe.com.human.api.model;

import java.io.Serializable;

public class Custom implements Serializable {

	private static final long serialVersionUID = 1L;

	public Custom(Hex hex) {
		this.hex = hex;
	}

	public Custom() {
	}

	private Hex hex;

	public Hex getHex() {
		return hex;
	}

	public void setHex(Hex hex) {
		this.hex = hex;
	}

}
