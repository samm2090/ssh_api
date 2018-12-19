package pe.com.human.api.model;

public class Action {

	private String tipo;
	private ResItem resItem;
	private Phone phone;
	private Location location;
	private Email email;
	private Edit edit;
	private Navigation navigation;
	private Linea primeraLinea;
	private Linea segundaLinea;
	private Copy copy;

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public Linea getPrimeraLinea() {
		return primeraLinea;
	}

	public void setPrimeraLinea(Linea primeraLinea) {
		this.primeraLinea = primeraLinea;
	}

	public Linea getSegundaLinea() {
		return segundaLinea;
	}

	public void setSegundaLinea(Linea segundaLinea) {
		this.segundaLinea = segundaLinea;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

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
