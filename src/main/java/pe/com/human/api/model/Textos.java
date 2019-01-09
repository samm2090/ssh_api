package pe.com.human.api.model;

public class Textos {

	private Texto envio;
	private Texto respuesta;

	public Textos(Texto envio, Texto respuesta) {
		this.envio = envio;
		this.respuesta = respuesta;
	}

	public Texto getEnvio() {
		return envio;
	}

	public void setEnvio(Texto envio) {
		this.envio = envio;
	}

	public Texto getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Texto respuesta) {
		this.respuesta = respuesta;
	}

}
