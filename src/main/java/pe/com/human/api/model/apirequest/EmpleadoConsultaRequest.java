package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoConsultaRequest {
	@JsonProperty("base")
	private Base base;
	@JsonProperty("empleado")
	private Empleado empleado;
	@JsonProperty("consulta")
	private Consulta consulta;

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

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

	public static class Empleado {
		@JsonProperty("id")
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	public static class Consulta {
		@JsonProperty("id")
		private String id;
		@JsonProperty("idTipo")
		private String idTipo;
		@JsonProperty("mensaje")
		private String mensaje;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIdTipo() {
			return idTipo;
		}

		public void setIdTipo(String idTipo) {
			this.idTipo = idTipo;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
	}

}
