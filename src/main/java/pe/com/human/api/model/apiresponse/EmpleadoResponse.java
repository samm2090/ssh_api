package pe.com.human.api.model.apiresponse;

public class EmpleadoResponse {
    private Data data;

    public EmpleadoResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

    }
}
