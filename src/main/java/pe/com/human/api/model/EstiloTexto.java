package pe.com.human.api.model;

import java.io.Serializable;

public class EstiloTexto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Font font;
	private Color color;

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
