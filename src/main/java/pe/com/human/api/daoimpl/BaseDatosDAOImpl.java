package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

/**
 * 
 * @author smuroy
 *
 */
@Repository
public class BaseDatosDAOImpl implements BaseDatosDAO {

	@Autowired
	PropertiesReader lector;

	@Override
	public ConfiguracionDataSource buscarConfiguracionXId(int baseDatos) {
		ConfiguracionDataSource configuracionBD = null;

		String query = lector.leerPropiedad("queries/baseDatos.query").getProperty("buscarConfiguracionXNombre");
		Connection conexion = null;
		try {
			ConfiguracionDataSource bdConfig = new ConfiguracionDataSource();
			bdConfig.setNombre("APP_MOVIL");
			bdConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			bdConfig.setUrl("jdbc:oracle:thin:@192.168.10.137:1521:orcl");
			bdConfig.setUsername("APP_MOVIL");
			bdConfig.setPassword("APP_MOVIL");

			conexion = ConexionBaseDatos.obtenerConexion(bdConfig);

			PreparedStatement buscarConexion = conexion.prepareStatement(query);
			buscarConexion.setInt(1, baseDatos);

			ResultSet rs = buscarConexion.executeQuery();

			if (rs.next()) {
				configuracionBD = new ConfiguracionDataSource();
				configuracionBD.setNombre(rs.getString("BD_D_NOMBRE"));
				configuracionBD.setDriverClassName(rs.getString("BD_D_DRIVER"));
				configuracionBD.setUrl(rs.getString("URL"));
				configuracionBD.setUsername(rs.getString("BD_D_USER"));
				configuracionBD.setPassword(rs.getString("BD_D_PASS"));
			}

			rs.close();
			buscarConexion.close();

			if (configuracionBD == null) {
				throw new ExcepcionBDNoResponde();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBDNoResponde();
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return configuracionBD;
	}

}
