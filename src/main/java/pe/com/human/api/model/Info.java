package pe.com.human.api.model;

import java.util.Map;

public class Info {

	private Alerta alerta;
	private ResItem resItem;
	private Map<String, Object> chat;

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public ResItem getResItem() {
		return resItem;
	}

	public void setResItem(ResItem resItem) {
		this.resItem = resItem;
	}

	public Map<String, Object> getChat() {
		return chat;
	}

	public void setChat(Map<String, Object> chat) {
		this.chat = chat;
	}

}
