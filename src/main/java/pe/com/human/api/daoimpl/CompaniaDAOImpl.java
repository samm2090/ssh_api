package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Sucursal;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Repository
public class CompaniaDAOImpl implements CompaniaDAO {

	@Autowired
	PropertiesReader lector;

	private static Logger logger = Logger.getLogger(CompaniaDAOImpl.class);

	@Override
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento) {
		List<Map<String, Object>> resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("listarCompaniasXDocumento");

		try {
			ConfiguracionDataSource configuracion = new ConfiguracionDataSource();
			configuracion.setNombre("APP_MOVIL");
			configuracion.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			configuracion.setUrl("jdbc:oracle:thin:@192.168.10.137:1521:orcl");
			configuracion.setUsername("APP_MOVIL");
			configuracion.setPassword("APP_MOVIL");
			
			conexion = ConexionBaseDatos.obtenerConexion(configuracion);

			PreparedStatement listarCompanias = conexion.prepareStatement(query);
			listarCompanias.setString(1, documento);

			ResultSet rs = listarCompanias.executeQuery();

			Compania compania;
			Sucursal sucursal;
			Map<String, Object> companiaBase;
			resultado = new ArrayList<>();
			while (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("COM_C_HUMAN"));
				compania.setNombre(rs.getString("COM_D_NOMBRE"));

				sucursal = new Sucursal();
				sucursal.setId(rs.getString("SUC_C_HUMAN"));
				sucursal.setNombre(rs.getString("SUC_D_NOMBRE"));

				compania.setSucursal(sucursal);

				companiaBase = new HashMap<>();

				companiaBase.put("compania", compania);
				
				companiaBase.put("baseDatos", rs.getInt("BD_I_ID"));

				resultado.add(companiaBase);
			}
			rs.close();
			listarCompanias.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	@Override
	public Compania buscarCompaniaXEmpleado(String idCompania, String idSucursal, String idEmpleado, String contrasenia,
			ConfiguracionDataSource configuracionDataSource) {

		Compania compania = null;

		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("buscarCompaniaXEmpleado");

		logger.info(query);
		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			logger.info(configuracionDataSource.getDriverClassName());
			logger.info(conexion);

			PreparedStatement buscarCompania = conexion.prepareStatement(query);
			buscarCompania.setString(1, idCompania);
			buscarCompania.setString(2, idSucursal);
			buscarCompania.setString(3, idEmpleado);

			ResultSet rs = buscarCompania.executeQuery();

			compania = new Compania();
			while (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("EBCODCIA"));
				compania.setNombre(rs.getString("EBDESCIA"));

				Sucursal sucursal = new Sucursal();
				sucursal.setId(rs.getString("EBCODSUC"));
				sucursal.setNombre(rs.getString("EBDESSUC"));

				compania.setSucursal(sucursal);
			}
			rs.close();
			buscarCompania.close();

			logger.info(compania.getNombre());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return compania;
	}

}
