package pe.com.human.api.model;

public class Extra {

	private String id;
	private String mes;
	private String anio;
	
	public Extra(String id) {
		this.id = id;
	}
	
	public Extra(String mes, String anio) {
		this.mes = mes;
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
