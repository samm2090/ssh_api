package pe.com.human.api.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DatosPersonales {
    @JsonProperty("avatar")
    private Avatar avatar;
    @JsonProperty("nombre")
    private NombrePersonal nombre;
    @JsonProperty("documento")
    private DocumentoPersonal documento;
    @JsonProperty("direccion")
    private DireccionPersonal direccion;
    @JsonProperty("estadoCivil")
    private EstadoCivil estadoCivil;
    @JsonProperty("email")
    private String email;
    @JsonProperty("telefonos")
    private List<Telefono> telefonoLst;

    public DatosPersonales(Avatar avatar, NombrePersonal nombre, DocumentoPersonal documento, DireccionPersonal direccion,
                           EstadoCivil estadoCivil, String email, List<Telefono> telefonoLst) {
        this.avatar = avatar;
        this.nombre = nombre;
        this.documento = documento;
        this.direccion = direccion;
        this.estadoCivil = estadoCivil;
        this.email = email;
        this.telefonoLst = telefonoLst;
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

    public DocumentoPersonal getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoPersonal documento) {
        this.documento = documento;
    }

    public DireccionPersonal getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionPersonal direccion) {
        this.direccion = direccion;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Telefono> getTelefonoLst() {
        return telefonoLst;
    }

    public void setTelefonoLst(List<Telefono> telefonoLst) {
        this.telefonoLst = telefonoLst;
    }
}
