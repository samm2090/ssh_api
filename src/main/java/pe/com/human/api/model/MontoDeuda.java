package pe.com.human.api.model;

public class MontoDeuda {

	private String frecuencia;
	private String moneda;
	private String monto;
	
	public MontoDeuda(String frecuencia, String moneda, String monto) {
		this.frecuencia = frecuencia;
		this.moneda = moneda;
		this.monto = monto;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

}
