package pe.com.human.api.dao;

import java.util.List;
import java.util.Map;

import pe.com.human.api.model.DeudaMes;
import pe.com.human.api.model.Prestamo;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface PrestamoDAO {

	Widget cantidadCuotasPendientes(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public Map<String, Object> deudaTotal(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<DeudaMes> deudaPorMes(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Prestamo> listarCuotas(String codcia, String codsuc, String codtra, String estado,
			ConfiguracionDataSource configuracionDataSource);
}
