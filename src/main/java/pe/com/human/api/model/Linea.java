package pe.com.human.api.model;

public class Linea {

	private Texto texto;
	private Action action;

	public Linea() {
	}

	public Linea(Texto texto) {
		this.texto = texto;
	}

	public Texto getTexto() {
		return texto;
	}

	public void setTexto(Texto texto) {
		this.texto = texto;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
