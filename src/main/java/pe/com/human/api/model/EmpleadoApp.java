package pe.com.human.api.model;

public class EmpleadoApp {

	private String id;
	private String idSucursal;
	private String idBaseDatos;
	private String documento;
	private String estado;
	private String codigoFirebase;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getIdBaseDatos() {
		return idBaseDatos;
	}

	public void setIdBaseDatos(String idBaseDatos) {
		this.idBaseDatos = idBaseDatos;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoFirebase() {
		return codigoFirebase;
	}

	public void setCodigoFirebase(String codigoFirebase) {
		this.codigoFirebase = codigoFirebase;
	}

}
