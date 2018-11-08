package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Armando Angulo
 */
public class ItemsResponse {
    @JsonProperty("data")
    private Data data;

    public ItemsResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("items")
        private List<Item> itemLst;

        public Data(List<Item> itemLst) {
            this.itemLst = itemLst;
        }

        public List<Item> getItemLst() {
            return itemLst;
        }

        public void setItemLst(List<Item> itemLst) {
            this.itemLst = itemLst;
        }
    }
}
