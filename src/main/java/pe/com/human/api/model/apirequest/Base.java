package pe.com.human.api.model.apirequest;

public class Base {
    private String baseDatos;
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
        private String id;
        private Compania.Sucursal sucursal;

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
