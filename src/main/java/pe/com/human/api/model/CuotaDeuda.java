package pe.com.human.api.model;

public class CuotaDeuda {

	private int numero;
	private String moneda;
	private String monto;
	
	public CuotaDeuda(int numero, String moneda, String monto) {
		this.numero = numero;
		this.moneda = moneda;
		this.monto = monto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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
