package pe.com.human.api.model;

import java.io.Serializable;

public class Datos implements Serializable {

	private static final long serialVersionUID = 1L;
	private DatosPersonales personales;
	private DatosLaborales laborales;

	public DatosPersonales getPersonales() {
		return personales;
	}

	public void setPersonales(DatosPersonales personales) {
		this.personales = personales;
	}

	public DatosLaborales getLaborales() {
		return laborales;
	}

	public void setLaborales(DatosLaborales laborales) {
		this.laborales = laborales;
	}

}
