package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telefono {
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("codigoPais")
    private String codigoPais;
    @JsonProperty("codigoCiudad")
    private String codigoCiudad;
    @JsonProperty("numero")
    private String numero;

    public Telefono(String tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public Telefono(String tipo, String codigoPais, String codigoCiudad, String numero) {
        this.tipo = tipo;
        this.codigoPais = codigoPais;
        this.codigoCiudad = codigoCiudad;
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
