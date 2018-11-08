package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Armando Angulo
 */
public class Icon {
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("url")
    private String url;

    public Icon(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
