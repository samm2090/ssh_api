package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.PrestamoDAO;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class PrestamoDAOImpl implements PrestamoDAO {

	@Autowired
	PropertiesReader lector;

	static final String TITLE_BOLETAS = "Prestamos";
	static final String SUBTITLE_BOLETAS = "Cuentas Pendientes";

	@Override
	public Widget cantidadCuotasPendientes(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		
		Widget resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/prestamo.query").getProperty("cantidadCuotasPendientes");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement calcularCantidad = conexion.prepareStatement(query);
			calcularCantidad.setString(1, idCompania);
			calcularCantidad.setString(2, idSucursal);
			calcularCantidad.setString(3, idEmpleado);

			ResultSet rs = calcularCantidad.executeQuery();

			if (rs.next()) {
				resultado = new Widget();

				resultado.setTitle(TITLE_BOLETAS);
				resultado.setSubtitle(SUBTITLE_BOLETAS);
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
