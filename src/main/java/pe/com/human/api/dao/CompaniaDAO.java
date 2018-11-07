package pe.com.human.api.dao;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD:src/main/java/pe/com/human/servicios/dao/CompaniaDAO.java
import pe.com.human.servicios.model.Compania;
import pe.com.human.servicios.util.ConfiguracionDataSource;
=======
import pe.com.human.api.util.ConfiguracionDataSource;
>>>>>>> 330bf0b4f117530366c1ec3e1b8295563765934a:src/main/java/pe/com/human/api/dao/CompaniaDAO.java

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface CompaniaDAO {
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento, ConfiguracionDataSource configuracion);

	public Compania buscarCompaniaXEmpleado(String idCompania, String idSucursal, String documento,
			String contrasenia, ConfiguracionDataSource configuracionDataSource);
}
