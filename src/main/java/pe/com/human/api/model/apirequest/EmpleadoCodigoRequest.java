package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoCodigoRequest {
	@JsonProperty("base")
	private Base base;
	@JsonProperty("empleado")
	private Empleado empleado;
	@JsonProperty("firebase")
	private Firebase firebase;

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Firebase getFirebase() {
		return firebase;
	}

	public void setFirebase(Firebase firebase) {
		this.firebase = firebase;
	}

	public static class Empleado {
		@JsonProperty("documento")
		private String documento;

		public String getDocumento() {
			return documento;
		}

		public void setDocumento(String documento) {
			this.documento = documento;
		}

	}

	public static class Firebase {
		@JsonProperty("token")
		private String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

	}
}
