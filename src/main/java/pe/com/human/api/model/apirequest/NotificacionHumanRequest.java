package pe.com.human.api.model.apirequest;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificacionHumanRequest {

	@JsonProperty("base")
	private Base base;

	@JsonProperty("empleado")
	private Empleado empleado;

	@JsonProperty("titulo")
	private String titulo;

	@JsonProperty("mensaje")
	private String mensaje;

	@JsonProperty("extra")
	private Map<String, String> extra;

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Map<String, String> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
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

}
