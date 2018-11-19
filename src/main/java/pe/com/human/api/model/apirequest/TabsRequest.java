package pe.com.human.api.model.apirequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TabsRequest {
    @JsonProperty("base")
    private Base base;
    @JsonProperty("menu")
    private Menu menu;
    @JsonProperty("empleado")
    private Empleado empleado;
    
    public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public static class Menu {
        @JsonProperty("id")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    
    
    public static class Empleado {
        @JsonProperty("rol")
        String rol;

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
    }
}
