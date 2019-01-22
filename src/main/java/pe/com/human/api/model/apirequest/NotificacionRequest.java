package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificacionRequest {

	@JsonProperty("to")
	private String to;

	@JsonProperty("priority")
	private String priority;

	@JsonProperty("data")
	private Data data;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data {
		@JsonProperty("consultaId")
		private String consultaId;

		@JsonProperty("empleadoId")
		private String empleadoId;

		@JsonProperty("tipo")
		private String tipo;

		@JsonProperty("title")
		private String title;

		@JsonProperty("body")
		private String body;

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getConsultaId() {
			return consultaId;
		}

		public void setConsultaId(String consultaId) {
			this.consultaId = consultaId;
		}

		public String getEmpleadoId() {
			return empleadoId;
		}

		public void setEmpleadoId(String empleadoId) {
			this.empleadoId = empleadoId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

	}

}
