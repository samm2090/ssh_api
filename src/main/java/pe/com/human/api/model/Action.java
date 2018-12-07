package pe.com.human.api.model;

public class Action {

	private String tipo;
	private ResItem resItem;
	private Phone phone;
	private Location location;
	private Edit edit;
	private Navigation navigation;

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Edit getEdit() {
		return edit;
	}

	public void setEdit(Edit edit) {
		this.edit = edit;
	}

	public Navigation getNavigation() {
		return navigation;
	}

	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public ResItem getResItem() {
		return resItem;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

}
