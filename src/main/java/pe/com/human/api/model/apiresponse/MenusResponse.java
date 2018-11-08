package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Armando Angulo
 */
public class MenusResponse {
    @JsonProperty("data")
    private Data data;

    public MenusResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("menus")
        private List<Menu> menuLst;

        public Data(List<Menu> menuLst) {
            this.menuLst = menuLst;
        }

        public List<Menu> getMenuLst() {
            return menuLst;
        }

        public void setMenuLst(List<Menu> menuLst) {
            this.menuLst = menuLst;
        }
    }
}
