package pe.com.human.api.model.apiresponse;

import java.io.Serializable;

import pe.com.human.api.model.Datos;
import pe.com.human.api.model.ResItem;

public class Empleado implements Serializable{
	
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