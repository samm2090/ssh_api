package pe.com.human.api.dao;

import pe.com.human.api.util.ConfiguracionDataSource;

public interface BaseDatosDAO {
	public ConfiguracionDataSource buscarConfiguracionXId(int baseDatos);
}
