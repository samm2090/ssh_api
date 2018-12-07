package pe.com.human.api.model;

public class Widget {
	private Texto titulo;
	private ResItem resItem;
	private Texto valor;
	private Texto subtitulo;
	private Color color;

	public Texto getTitulo() {
		return titulo;
	}

	public void setTitulo(Texto titulo) {
		this.titulo = titulo;
	}

	public ResItem getResItem() {
		return resItem;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

	public Texto getValor() {
		return valor;
	}

	public void setValor(Texto valor) {
		this.valor = valor;
	}

	public Texto getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(Texto subtitulo) {
		this.subtitulo = subtitulo;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
