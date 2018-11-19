package pe.com.human.api.model;

/**
 * 
 * @author smuroy
 *
 */
public class Menu {
	private int id;
	private String nombre;
	private int idTipo;
	private int orden;
	private int idIcono;
	private String navegacion;
	private int idRol;
	private int idPadre;
	private int idVisibilidad;
	private int idEstado;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public int getIdIcono() {
		return idIcono;
	}
	public void setIdIcono(int idIcono) {
		this.idIcono = idIcono;
	}
	public String getNavegacion() {
		return navegacion;
	}
	public void setNavegacion(String navegacion) {
		this.navegacion = navegacion;
	}
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public int getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}
	public int getIdVisibilidad() {
		return idVisibilidad;
	}
	public void setIdVisibilidad(int idVisibilidad) {
		this.idVisibilidad = idVisibilidad;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
}
