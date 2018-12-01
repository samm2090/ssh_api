package pe.com.human.api.dao;

import java.util.List;

import pe.com.human.api.model.Assets;
import pe.com.human.api.model.Compania;

public interface AssetsDAO {

	List<Assets> listarAssetsXCompania(Compania compania);

}
