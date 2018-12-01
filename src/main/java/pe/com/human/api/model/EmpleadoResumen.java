package pe.com.human.api.model;

import java.io.Serializable;

public class EmpleadoResumen implements Serializable{

	private static final long serialVersionUID = 1L;
	private ResItem resItem;
	private Datos datos;

	public ResItem getResItem() {
		return resItem;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}

}
