package pe.com.human.api.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class Compania implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private Sucursal sucursal;
	private Estilo estilo;
	
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
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
}
