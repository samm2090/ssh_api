package pe.com.human.api.model;

import java.io.Serializable;

public class DatosPersonales implements Serializable {

	private static final long serialVersionUID = 1L;
	private NombreCompleto nombre;
	private Documento documento;

	public NombreCompleto getNombre() {
		return nombre;
	}

	public void setNombre(NombreCompleto nombre) {
		this.nombre = nombre;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
