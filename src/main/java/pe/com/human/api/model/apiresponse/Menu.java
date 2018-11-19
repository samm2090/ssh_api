package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Armando Angulo
 */
public class Menu {
	@JsonProperty("id")
	private String id;
	@JsonProperty("order")
	private int order;
	@JsonProperty("icon")
	private Icon icon;
	@JsonProperty("title")
	private String title;
	@JsonProperty("navigation")
	private String navigation;
	@JsonProperty("show")
	private String show;

	public Menu() {
	};

	public Menu(String id, int order, Icon icon, String title, String navigation, String show) {
		this.id = id;
		this.order = order;
		this.icon = icon;
		this.title = title;
		this.navigation = navigation;
		this.show = show;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
}
