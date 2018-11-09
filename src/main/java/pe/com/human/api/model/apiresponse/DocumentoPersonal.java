package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentoPersonal {
    @JsonProperty("documento")
    private Documento documento;
    @JsonProperty("numero")
    private String numero;

    public DocumentoPersonal(Documento documento, String numero) {
        this.documento = documento;
        this.numero = numero;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
