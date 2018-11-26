package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.EvaluacionDesempenioDAO;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class EvaluacionDesempenioDAOImpl implements EvaluacionDesempenioDAO {
	
	static final String TITTLE_EVD = "EVD";
	static final String SUBTITLE_EVD = "Aceptable";

	@Autowired
	PropertiesReader lector;

	@Override
	public Widget promedioNotaDesempenio(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		
		Widget resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/evd.query").getProperty("promedioEvaluacion");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement calcularCantidad = conexion.prepareStatement(query);
			calcularCantidad.setString(1, idCompania);
			calcularCantidad.setString(2, idSucursal);
			calcularCantidad.setString(3, idEmpleado);

			ResultSet rs = calcularCantidad.executeQuery();

			if (rs.next()) {
				resultado = new Widget();

				resultado.setTitle(TITTLE_EVD);
				resultado.setSubtitle(SUBTITLE_EVD);
				resultado.setValor(rs.getString("SALDO"));
			}
			rs.close();
			calcularCantidad.close();

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
