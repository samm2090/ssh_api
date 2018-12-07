package pe.com.human.api.model;

/**
 * 
 * @author smuroy
 *
 */
public class ItemCumpleanos {

	private String tipo;
	private ResItem resItem;
	private Linea primeraLinea;
	private Linea segundaLinea;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public ResItem getResItem() {
		return resItem;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

	public Linea getPrimeraLinea() {
		return primeraLinea;
	}

	public void setPrimeraLinea(Linea primeraLinea) {
		this.primeraLinea = primeraLinea;
	}

	public Linea getSegundaLinea() {
		return segundaLinea;
	}

	public void setSegundaLinea(Linea segundaLinea) {
		this.segundaLinea = segundaLinea;
	}

}
