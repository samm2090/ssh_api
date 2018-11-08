package pe.com.human.api.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author smuroy
 *
 */
public class Icon implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String url;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
