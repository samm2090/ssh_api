package pe.com.human.api.model;

import java.io.Serializable;

public class Archivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String almaTipo;
	private String tipo;
	private Local local;
	private Remote remote;

	public String getAlmaTipo() {
		return almaTipo;
	}

	public void setAlmaTipo(String almaTipo) {
		this.almaTipo = almaTipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Remote getRemote() {
		return remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}

}
