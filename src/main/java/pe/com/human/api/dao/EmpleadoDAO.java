package pe.com.human.api.dao;

import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.model.apiresponse.EmpleadoResumenResponse;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
public interface EmpleadoDAO {

	public Empleado buscarEmpleadoXUsuario(String idCompania, String idSucursal, String documento,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);

	public Widget cantidadSubordinados(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	EmpleadoResumenResponse getEmpleadoResumen(EmpleadoRequest request);

}
