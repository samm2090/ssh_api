package pe.com.human.api.model;

import java.util.List;

public class Directorio {
	private String tipo;
	private Texto area;
	private List<Item> valores;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Texto getArea() {
		return area;
	}

	public void setArea(Texto area) {
		this.area = area;
	}

	public List<Item> getValores() {
		return valores;
	}

	public void setValores(List<Item> valores) {
		this.valores = valores;
	}

}
