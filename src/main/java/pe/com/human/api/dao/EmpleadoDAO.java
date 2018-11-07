package pe.com.human.api.dao;

import pe.com.human.api.model.Empleado;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
public interface EmpleadoDAO {

	public Empleado buscarEmpleadoXUsuario(String idCompania, String idSucursal, String documento,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);

}
