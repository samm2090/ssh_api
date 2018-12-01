package pe.com.human.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author smuroy
 *
 */
public class Color implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tipo;
	private String uso;
	private Custom custom;
	private Default default1;
	
	@JsonProperty("default")	
	public Default getDefault1() {
		return default1;
	}

	@JsonProperty("default")
	public void setDefault1(Default default1) {
		this.default1 = default1;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}
}
