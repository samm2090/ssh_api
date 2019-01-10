package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoVacSolRequest {
	@JsonProperty("base")
	private Base base;
	@JsonProperty("empleado")
	private Empleado empleado;
	@JsonProperty("vacaciones")
	private Vacaciones vacaciones;

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

	public Vacaciones getVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(Vacaciones vacaciones) {
		this.vacaciones = vacaciones;
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

	public static class Vacaciones {
		@JsonProperty("categoria")
		private String categoriaVacaciones;

		@JsonProperty("fechaInicio")
		private String fechaInicial;

		@JsonProperty("fechaFinal")
		private String fechaFinal;

		public String getCategoriaVacaciones() {
			return categoriaVacaciones;
		}

		public void setCategoriaVacaciones(String categoriaVacaciones) {
			this.categoriaVacaciones = categoriaVacaciones;
		}

		public String getFechaInicial() {
			return fechaInicial;
		}

		public void setFechaInicial(String fechaInicial) {
			this.fechaInicial = fechaInicial;
		}

		public String getFechaFinal() {
			return fechaFinal;
		}

		public void setFechaFinal(String fechaFinal) {
			this.fechaFinal = fechaFinal;
		}

	}
}
