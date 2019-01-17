package pe.com.human.api.model;

public class Extra {

	private String id;
	private int mes;
	private int anio;
	private String area;
	private String estatus;

	public Extra() {
	}

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
