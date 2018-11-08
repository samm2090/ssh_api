package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Armando Angulo
 */
public class TabsItemsRequest {
    @JsonProperty("base")
    private Base base;
    @JsonProperty("tab")
    private Tab tab;

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    private static class Tab {
        @JsonProperty("id")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
