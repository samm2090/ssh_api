package pe.com.human.api.model;

import java.util.List;

public class VacacionesSolicitadas {

	private String tipo;
	private List<Item> items;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
