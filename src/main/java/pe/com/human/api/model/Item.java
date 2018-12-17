package pe.com.human.api.model;

import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class Item {

	private String tipo;
	private ResItem resItem;
	private Linea primeraLinea;
	private Linea segundaLinea;
	private Linea terceraLinea;
	private Linea cuartaLinea;
	private List<Action> action;

	public List<Action> getAction() {
		return action;
	}

	public void setAction(List<Action> action) {
		this.action = action;
	}

	public Linea getTerceraLinea() {
		return terceraLinea;
	}

	public void setTerceraLinea(Linea terceraLinea) {
		this.terceraLinea = terceraLinea;
	}

	public Linea getCuartaLinea() {
		return cuartaLinea;
	}

	public void setCuartaLinea(Linea cuartaLinea) {
		this.cuartaLinea = cuartaLinea;
	}

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
