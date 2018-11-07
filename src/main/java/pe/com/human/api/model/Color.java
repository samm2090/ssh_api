package pe.com.human.api.model;

import java.io.Serializable;

/**
 * 
 * @author smuroy
 *
 */
public class Color implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String valor;
	private String tipo;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
