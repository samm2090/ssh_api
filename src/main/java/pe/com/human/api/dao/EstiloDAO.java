package pe.com.human.api.dao;

import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Estilo;
import pe.com.human.api.util.ConfiguracionDataSource;

public interface EstiloDAO {
	
	public Estilo buscarEstiloXCompania(Compania compania, ConfiguracionDataSource configBaseAppMovil);

}
