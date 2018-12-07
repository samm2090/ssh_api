package pe.com.human.api.dao;

import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConfiguracionDataSource;


/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface EvaluacionDesempenioDAO {
	public Widget promedioNotaDesempenio(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);
}