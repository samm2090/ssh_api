package pe.com.human.api.dao;

import java.util.List;

import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
public interface MenuDAO {

	public List<Menu> buscarMenu(String codcia, int idTipo, String rol,
			ConfiguracionDataSource configuracionDataSource);
	
	public List<Menu> buscarSubMenu(String codcia, int idTipo, String rol, int idPadre,
			ConfiguracionDataSource configuracionDataSource);

}
