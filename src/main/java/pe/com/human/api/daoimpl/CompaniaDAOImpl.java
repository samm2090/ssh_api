package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento,
			ConfiguracionDataSource configuracion) {
		List<Map<String, Object>> resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("listarCompaniasXDocumento");

		try {
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
				compania.setId(rs.getString("EBCODCIA"));
				compania.setNombre(rs.getString("EBDESCIA"));

				sucursal = new Sucursal();
				sucursal.setId(rs.getString("EBCODSUC"));
				sucursal.setNombre(rs.getString("EBDESSUC"));

				compania.setSucursal(sucursal);

				companiaBase = new HashMap<>();

				companiaBase.put("compania", compania);
				companiaBase.put("baseDatos", configuracion.getNombre());

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

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

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
		return compania;
	}

}
