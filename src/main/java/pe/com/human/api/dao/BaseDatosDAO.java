package pe.com.human.api.dao;

import pe.com.human.api.util.ConfiguracionDataSource;

public interface BaseDatosDAO {
	public ConfiguracionDataSource buscarConfiguracionXNombre(int baseDatos);
}
