package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectorioDetalleRequest {
	@JsonProperty("base")
	private Base base;
	@JsonProperty("id")
	private String id;

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
