package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.MenuDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class MenuDAOImpl implements MenuDAO {

	@Autowired
	PropertiesReader lector;

	@Override
	public List<Menu> buscarMenu(String codcia, int idTipo, String rol,
			ConfiguracionDataSource configuracionDataSource) {
		List<Menu> resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/menu.query").getProperty("buscarMenu");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarMenu = conexion.prepareStatement(query);
			buscarMenu.setString(1, codcia);
			buscarMenu.setInt(2, idTipo);
			buscarMenu.setString(3, rol);

			ResultSet rs = buscarMenu.executeQuery();

			resultado = new ArrayList<>();
			Menu menu;
			while (rs.next()) {
				menu = new Menu();
				menu.setId(String.valueOf(rs.getInt("MEN_I_ID")));
				menu.setOrder(rs.getInt("MEN_N_ORDEN"));
				menu.setTitle(rs.getString("MEN_D_NOMBRE"));
				menu.setNavigation(rs.getString("MEN_D_NAVEGACION"));
				menu.setShow(String.valueOf(rs.getInt("MEN_I_VISIBILIDAD")));
				// menu.setIcon(rs.getInt("MEN_I_ICONO"));

				resultado.add(menu);
			}
			rs.close();
			buscarMenu.close();

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
		return resultado;
	}

	@Override
	public List<Menu> buscarSubMenu(String codcia, int idTipo, String rol, int idPadre,
			ConfiguracionDataSource configuracionDataSource) {
		List<Menu> resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/menu.query").getProperty("buscarSubMenu");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarMenu = conexion.prepareStatement(query);
			buscarMenu.setString(1, codcia);
			buscarMenu.setInt(2, idTipo);
			buscarMenu.setString(3, rol);
			buscarMenu.setInt(4, idPadre);

			ResultSet rs = buscarMenu.executeQuery();

			resultado = new ArrayList<>();
			Menu tab;
			while (rs.next()) {
				tab = new Menu();
				tab.setId(String.valueOf(rs.getInt("MEN_I_ID")));
				tab.setOrder(rs.getInt("MEN_N_ORDEN"));
				tab.setTitle(rs.getString("MEN_D_NOMBRE"));
				tab.setNavigation(rs.getString("MEN_D_NAVEGACION"));
				tab.setShow(String.valueOf(rs.getInt("MEN_I_VISIBILIDAD")));
				// menu.setIcon(rs.getInt("MEN_I_ICONO"));

				resultado.add(tab);
			}
			rs.close();
			buscarMenu.close();

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
		return resultado;
	}

}
