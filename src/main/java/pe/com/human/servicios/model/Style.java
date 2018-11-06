package pe.com.human.servicios.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class Style implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Logo logo;
	private List<Color> colores;

	public Logo getLogo() {
		return logo;
	}
	public void setLogo(Logo logo) {
		this.logo = logo;
	}
	public List<Color> getColores() {
		return colores;
	}
	public void setColores(List<Color> colores) {
		this.colores = colores;
	}
	
}
