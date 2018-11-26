package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.AsistenciaDAO;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class AsistenciaDAOImpl implements AsistenciaDAO {

	@Autowired
	PropertiesReader lector;
	
	static final String TITLE_ASISTENCIA = "Asistencia";
	static final String SUBTITLE_ASISTENCIA = "Aceptable";


	@Override
	public Widget cantidadAsistenciaMesActual(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		
		Widget resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/asistencia.query").getProperty("cantidadAsistenciaMesActual");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement calcularCantidad = conexion.prepareStatement(query);
			calcularCantidad.setString(1, idCompania);
			calcularCantidad.setString(2, idSucursal);
			calcularCantidad.setString(3, idEmpleado);

			ResultSet rs = calcularCantidad.executeQuery();

			if (rs.next()) {
				resultado = new Widget();

				resultado.setTitle(TITLE_ASISTENCIA);
				resultado.setSubtitle(SUBTITLE_ASISTENCIA);
				resultado.setValor(rs.getString("CANTIDAD"));
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
