package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TabsResponse {
    @JsonProperty("data")
    private Data data;

    public TabsResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("tabs")
        private List<Tab> tabLst;

        public Data(List<Tab> tabLst) {
            this.tabLst = tabLst;
        }

        public List<Tab> getTabLst() {
            return tabLst;
        }

        public void setTabLst(List<Tab> tabLst) {
            this.tabLst = tabLst;
        }
    }
}
