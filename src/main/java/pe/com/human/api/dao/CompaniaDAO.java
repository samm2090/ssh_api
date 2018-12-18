package pe.com.human.api.dao;

import java.util.List;
import java.util.Map;

import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Directorio;
import pe.com.human.api.util.ConfiguracionDataSource;


/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface CompaniaDAO {
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento);

	public Compania buscarCompaniaXEmpleado(String idCompania, String idSucursal, String idEmpleado,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);

	public Compania buscarCompaniaXId(int id);

	public List<Directorio> buscarDirectorioXEmpleadoArea(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Directorio> buscarDirectorioCriterio(String codcia, String codsuc, String criterio,
			ConfiguracionDataSource configuracionDataSource);

	public List<Directorio> buscarDirectorioXEmpleado(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);
}
