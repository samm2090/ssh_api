package pe.com.human.servicios.dao;

import java.util.List;
import java.util.Map;

import pe.com.human.servicios.model.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface CompaniaDAO {
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento, ConfiguracionDataSource configuracion);
}
