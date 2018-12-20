package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseRequest {

	@JsonProperty("base")
	private Base base;

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

}
