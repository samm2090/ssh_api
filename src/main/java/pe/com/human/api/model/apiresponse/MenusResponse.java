package pe.com.human.api.model.apiresponse;

import java.util.List;

public class MenusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private static class Data {
        private List<Menu> menu;

        public List<Menu> getMenu() {
            return menu;
        }

        public void setMenu(List<Menu> menu) {
            this.menu = menu;
        }
    }
}
