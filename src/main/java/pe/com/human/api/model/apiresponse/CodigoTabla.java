package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodigoTabla {
    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("abreviatura")
    private String abreviatura;
    @JsonProperty("descripcion")
    private String descripcion;

    public CodigoTabla(String codigo, String abreviatura, String descripcion) {
        this.codigo = codigo;
        this.abreviatura = abreviatura;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
