package pe.com.human.api.dao;

import java.util.List;
import java.util.Map;

import pe.com.human.api.model.Compania;
import pe.com.human.api.util.ConfiguracionDataSource;


/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface CompaniaDAO {
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento, ConfiguracionDataSource configuracion);

	public Compania buscarCompaniaXEmpleado(String idCompania, String idSucursal, String idEmpleado,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);
}
