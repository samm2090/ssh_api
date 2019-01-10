package pe.com.human.api.dao;

import pe.com.human.api.model.Vacaciones;
import pe.com.human.api.model.VacacionesSolicitadas;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface VacacionesDAO {

	Widget cantidadSaldo(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public Vacaciones resumenVacaciones(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public VacacionesSolicitadas listarSolicitudVacaciones(String codcia, String codsuc, String codtra, String[] flgEst,
			int rownum, ConfiguracionDataSource configuracionDataSource);

	public VacacionesSolicitadas listarSolicitudVacacionesSimple(String codcia, String codsuc, String codtra,
			String[] flgEst, int rownum, ConfiguracionDataSource configuracionDataSource);

	boolean insertarSolicitud(String codcia, String codsuc, String codtra, String categoriaVacaciones,
			String fechaInicial, String fechaFinal, ConfiguracionDataSource configuracionDataSource);

}
