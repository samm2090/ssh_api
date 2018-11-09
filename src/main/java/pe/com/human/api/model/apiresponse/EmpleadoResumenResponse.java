package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpleadoResumenResponse {
    @JsonProperty("data")
    private Data data;

    public EmpleadoResumenResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("avatar")
        private Avatar avatar;
        @JsonProperty("nombre")
        private NombrePersonal nombre;
        @JsonProperty("puesto")
        private CodigoTabla puesto;

        public Data(Avatar avatar, NombrePersonal nombre, CodigoTabla puesto) {
            this.avatar = avatar;
            this.nombre = nombre;
            this.puesto = puesto;
        }

        public Avatar getAvatar() {
            return avatar;
        }

        public void setAvatar(Avatar avatar) {
            this.avatar = avatar;
        }

        public NombrePersonal getNombre() {
            return nombre;
        }

        public void setNombre(NombrePersonal nombre) {
            this.nombre = nombre;
        }

        public CodigoTabla getPuesto() {
            return puesto;
        }

        public void setPuesto(CodigoTabla puesto) {
            this.puesto = puesto;
        }
    }
}
