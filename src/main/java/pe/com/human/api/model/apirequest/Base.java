package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Base {
    @JsonProperty("baseDatos")
    private String baseDatos;
    @JsonProperty("compania")
    private Base.Compania compania;

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    private static class Compania {
        @JsonProperty("id")
        private String id;
        @JsonProperty("sucursal")
        private Sucursal sucursal;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Sucursal getSucursal() {
            return sucursal;
        }

        public void setSucursal(Sucursal sucursal) {
            this.sucursal = sucursal;
        }

        private static class Sucursal {
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
}
