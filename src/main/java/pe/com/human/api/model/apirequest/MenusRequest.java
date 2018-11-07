package pe.com.human.api.model.apirequest;

public class MenusRequest {
    private Base base;
    private Empleado empleado;

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    private static class Empleado {
        String rol;

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
    }
}
