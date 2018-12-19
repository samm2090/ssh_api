package pe.com.human.api.model;

public class Copy {
	private String tipo;
	private String uri;

	public Copy() {
	}

	public Copy(String tipo, String uri) {
		this.tipo = tipo;
		this.uri = uri;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
