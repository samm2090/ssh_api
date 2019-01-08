package pe.com.human.api.model;

public class Prestamo {

	private Alerta alerta;
	private FechaDeuda fecha;
	private String tipo;
	private MontoDeuda monto;
	private CuotaDeuda cuota;

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public FechaDeuda getFecha() {
		return fecha;
	}

	public void setFecha(FechaDeuda fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public MontoDeuda getMonto() {
		return monto;
	}

	public void setMonto(MontoDeuda monto) {
		this.monto = monto;
	}

	public CuotaDeuda getCuota() {
		return cuota;
	}

	public void setCuota(CuotaDeuda cuota) {
		this.cuota = cuota;
	}

}
