package pe.com.human.api.model;

import java.util.List;

public class Directorio {
	private String tipo;
	private String area;
	private List<Item> valores;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Item> getValores() {
		return valores;
	}

	public void setValores(List<Item> valores) {
		this.valores = valores;
	}

}
