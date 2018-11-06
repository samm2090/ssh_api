package pe.com.human.servicios.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pe.com.human.servicios.dao.CompaniaDAO;
import pe.com.human.servicios.model.Compania;
import pe.com.human.servicios.model.Sucursal;
import pe.com.human.servicios.util.ConexionBaseDatos;
import pe.com.human.servicios.util.ConfiguracionDataSource;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Repository
public class CompaniaDAOImpl implements CompaniaDAO {

	// @Autowired
	// DataSource datasource;

	@Override
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento,
			ConfiguracionDataSource configuracion) {
		List<Map<String, Object>> resultado = null;
		Connection conexion = null;

		// *Optimizar query
		String query = "SELECT DISTINCT C.* FROM "
				+ "EBCOMPANIA C INNER JOIN HR_EMPLEADO E ON E.EMPCODCIA = C.EBCODCIA AND E.EMPCODSUC = C.EBCODSUC RIGHT JOIN EBUSUEMP UE "
				+ "ON UE.EMPCODTRA = E.EMPCODTRA AND UE.EMPCODCIA = E.EMPCODCIA AND UE.EMPCODSUC = E.EMPCODSUC "
				+ "WHERE E.EMPNRODOCID = ? AND E.EMPFLGEST = '1'";

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
				sucursal.setNombre(rs.getString("EBDESSUCC"));

				compania.setSucursal(sucursal);

				companiaBase = new HashMap<>();

				companiaBase.put("compania", compania);
				companiaBase.put("baseDatos", configuracion.getNombre());

				resultado.add(companiaBase);

			}

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

}
