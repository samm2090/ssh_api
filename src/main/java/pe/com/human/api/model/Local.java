package pe.com.human.api.model;

import java.io.Serializable;

public class Local implements Serializable {

	private static final long serialVersionUID = 1L;
	private String resTipo;
	private String nombre;
	private DimensionRatio dimensionRatio;
	private String ext;

	public Local() {
	}

	public Local(String nombre) {
		this.nombre = nombre;
	}

	public Local(String resTipo, String nombre, String ext) {
		this.resTipo = resTipo;
		this.nombre = nombre;
		this.ext = ext;
	}

	public String getResTipo() {
		return resTipo;
	}

	public void setResTipo(String resTipo) {
		this.resTipo = resTipo;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
