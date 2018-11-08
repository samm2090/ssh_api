package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Armando Angulo
 */
public class Tab {
    @JsonProperty("id")
    private String id;
    @JsonProperty("order")
    private int order;
    @JsonProperty("icon")
    private Icon icon;
    @JsonProperty("navigation")
    private String navigation;

    public Tab(String id, int order, Icon icon, String navigation) {
        this.id = id;
        this.order = order;
        this.icon = icon;
        this.navigation = navigation;
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

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }
}
