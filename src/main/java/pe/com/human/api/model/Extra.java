package pe.com.human.api.model;

public class Extra {

	private String id;
	private int mes;
	private int anio;

	public Extra(String id) {
		this.id = id;
	}

	public Extra(String id, int mes, int anio) {
		this.id = id;
		this.mes = mes;
		this.anio = anio;
	}

	public Extra(int mes, int anio) {
		this.mes = mes;
		this.anio = anio;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
