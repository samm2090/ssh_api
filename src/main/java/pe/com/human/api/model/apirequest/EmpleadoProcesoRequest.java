package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoProcesoRequest {
	@JsonProperty("base")
	private Base base;
	@JsonProperty("empleado")
	private Empleado empleado;

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

		@JsonProperty("idProceso")
		private String idProceso;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIdProceso() {
			return idProceso;
		}

		public void setIdProceso(String idProceso) {
			this.idProceso = idProceso;
		}
	}
}
