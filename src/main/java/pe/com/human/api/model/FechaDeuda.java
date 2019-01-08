package pe.com.human.api.model;

public class FechaDeuda {
	private String prestamo;
	private String vigencia;

	public FechaDeuda(String prestamo, String vigencia) {
		this.prestamo = prestamo;
		this.vigencia = vigencia;
	}

	public String getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(String prestamo) {
		this.prestamo = prestamo;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

}
