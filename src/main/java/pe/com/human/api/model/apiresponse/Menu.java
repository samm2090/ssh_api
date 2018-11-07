package pe.com.human.api.model.apiresponse;

public class Menu {
    private String id;
    private int order;
    private Icon icon;
    private String title;
    private String navigation;
    private String show;

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
