package pe.com.human.api.model;

public class DetalleDirectorio {
	private Texto nombre;
	private Texto puesto;
	private Texto sede;
	private ItemDetalle items;
	private ResItem resItem;

	public ResItem getResItem() {
		return resItem;
	}

	public Texto getSede() {
		return sede;
	}

	public void setSede(Texto sede) {
		this.sede = sede;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

	public ItemDetalle getItems() {
		return items;
	}

	public void setItems(ItemDetalle items) {
		this.items = items;
	}

	public Texto getNombre() {
		return nombre;
	}

	public void setNombre(Texto nombre) {
		this.nombre = nombre;
	}

	public Texto getPuesto() {
		return puesto;
	}

	public void setPuesto(Texto puesto) {
		this.puesto = puesto;
	}

}
