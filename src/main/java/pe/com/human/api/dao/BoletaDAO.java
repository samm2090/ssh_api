package pe.com.human.api.dao;

import java.util.List;

import pe.com.human.api.model.BoletaEmpleado;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface BoletaDAO {

	Widget cantidadPagosMesActual(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	List<BoletaEmpleado> listarBoletasXIdEmpleado(String codcia, String codsuc, String codtra, ConfiguracionDataSource configuracionDataSource);

	Widget cantidadBoletasNoLeidas(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

}
