package pe.com.human.servicios.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class Sucursal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
