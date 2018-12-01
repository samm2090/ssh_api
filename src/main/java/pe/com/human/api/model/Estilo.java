package pe.com.human.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class Estilo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Assets> assets;
	private List<Colores> colores;

	public List<Assets> getAssets() {
		return assets;
	}

	public void setAssets(List<Assets> assets) {
		this.assets = assets;
	}

	public List<Colores> getColores() {
		return colores;
	}

	public void setColores(List<Colores> colores) {
		this.colores = colores;
	}

}
