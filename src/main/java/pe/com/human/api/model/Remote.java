package pe.com.human.api.model;

import java.io.Serializable;

public class Remote implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resTipo;
	private String url;
	private String nombre;
	private DimensionRatio dimensionRatio;
	private String ext;

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getResTipo() {
		return resTipo;
	}

	public void setResTipo(String resTipo) {
		this.resTipo = resTipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DimensionRatio getDimensionRatio() {
		return dimensionRatio;
	}

	public void setDimensionRatio(DimensionRatio dimensionRatio) {
		this.dimensionRatio = dimensionRatio;
	}

}
