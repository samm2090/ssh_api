package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DatosLaborales {
    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("gerencia")
    private String gerencia;
    @JsonProperty("area")
    private String area;
    @JsonProperty("subArea")
    private String subArea;
    @JsonProperty("puesto")
    private String puesto;
    @JsonProperty("fechaIngreso")
    private Date fechaIngreso;
    @JsonProperty("email")
    private String email;

    public DatosLaborales(String codigo, String gerencia, String area, String subArea,
                          String puesto, Date fechaIngreso, String email) {
        this.codigo = codigo;
        this.gerencia = gerencia;
        this.area = area;
        this.subArea = subArea;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso;
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGerencia() {
        return gerencia;
    }

    public void setGerencia(String gerencia) {
        this.gerencia = gerencia;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
