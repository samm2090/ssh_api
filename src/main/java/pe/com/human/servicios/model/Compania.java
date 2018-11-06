package pe.com.human.servicios.model;

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
	private Style style;
	
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
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style style) {
		this.style = style;
	}
}
