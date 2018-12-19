package pe.com.human.api.model;

public class DetalleDirectorio {
	private Texto nombre;
	private Texto puesto;
	private ItemDetalle items;
	private ResItem resItem;

	public ResItem getResItem() {
		return resItem;
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
