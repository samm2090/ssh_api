package pe.com.human.api.model;

import java.util.List;

public class ItemDetalle {

	private String tipo;
	private List<Item> valores;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Item> getValores() {
		return valores;
	}

	public void setValores(List<Item> valores) {
		this.valores = valores;
	}

}
