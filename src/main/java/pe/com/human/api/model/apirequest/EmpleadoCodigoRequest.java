package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoCodigoRequest {
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
		@JsonProperty("documento")
		private String documento;

		@JsonProperty("codigo")
		private String codigo;

		public String getDocumento() {
			return documento;
		}

		public void setDocumento(String documento) {
			this.documento = documento;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

	}
}
