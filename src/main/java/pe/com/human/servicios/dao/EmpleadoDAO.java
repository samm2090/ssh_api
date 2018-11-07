package pe.com.human.servicios.dao;

import pe.com.human.servicios.model.Empleado;
import pe.com.human.servicios.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
public interface EmpleadoDAO {

	public Empleado buscarEmpleadoXUsuario(String idCompania, String idSucursal, String documento,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);

}
