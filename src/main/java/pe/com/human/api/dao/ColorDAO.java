package pe.com.human.api.dao;

import java.util.List;

import pe.com.human.api.model.Colores;
import pe.com.human.api.model.Compania;

public interface ColorDAO {

	List<Colores> listarColoresXCompania(Compania compania);

}
