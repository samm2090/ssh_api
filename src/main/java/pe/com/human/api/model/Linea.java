package pe.com.human.api.model;

public class Linea {

	private Texto texto;
	private Textos textos;
	private Action action;

	public Linea() {
	}

	public Linea(Texto texto) {
		this.texto = texto;
	}

	public Linea(Textos textos) {
		this.textos = textos;
	}

	public Textos getTextos() {
		return textos;
	}

	public void setTextos(Textos textos) {
		this.textos = textos;
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
